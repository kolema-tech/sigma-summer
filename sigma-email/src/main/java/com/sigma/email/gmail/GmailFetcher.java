package com.sigma.email.gmail;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Slf4j
public class GmailFetcher {

    private final Gmail gmail;
    private final String targetEmail;

    public GmailFetcher(Gmail gmail, String targetEmail) {
        this.gmail = gmail;
        this.targetEmail = targetEmail;
    }

    /**
     * Fetches unread emails. Does NOT mark them as read.
     * Caller must call markAsRead() after successful save.
     */
    public List<LocalEmail> fetchUnread() {
        try {
            ListMessagesResponse response = gmail.users().messages()
                    .list(targetEmail)
                    .setQ("is:unread in:inbox")
                    .execute();

            if (response.getMessages() == null || response.getMessages().isEmpty()) {
                log.debug("No unread emails found");
                return Collections.emptyList();
            }

            List<LocalEmail> results = new ArrayList<>();
            for (Message msgRef : response.getMessages()) {
                try {
                    Message full = gmail.users().messages()
                            .get(targetEmail, msgRef.getId())
                            .setFormat("full")
                            .execute();
                    results.add(parseEmail(full));
                } catch (Exception e) {
                    log.error("Failed to fetch message {}: {}", msgRef.getId(), e.getMessage(), e);
                }
            }
            return results;
        } catch (Exception e) {
            log.error("Failed to fetch unread emails: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * Marks a single email as read. Called by GmailScheduler only after
     * EmailStorageService.save() returns true.
     */
    public void markAsRead(String messageId) {
        try {
            ModifyMessageRequest request = new ModifyMessageRequest()
                    .setRemoveLabelIds(Collections.singletonList("UNREAD"));
            gmail.users().messages().modify(targetEmail, messageId, request).execute();
            log.debug("Marked message {} as read", messageId);
        } catch (Exception e) {
            log.warn("Failed to mark message {} as read: {}", messageId, e.getMessage());
        }
    }

    private LocalEmail parseEmail(Message message) {
        LocalEmail email = new LocalEmail();
        email.setMessageId(message.getId());
        email.setFetchedAt(Instant.now());

        if (message.getInternalDate() != null) {
            email.setReceivedAt(Instant.ofEpochMilli(message.getInternalDate()));
        }

        MessagePart payload = message.getPayload();
        if (payload != null) {
            for (MessagePartHeader header : safeList(payload.getHeaders())) {
                switch (header.getName()) {
                    case "From":
                        email.setFrom(header.getValue());
                        break;
                    case "To":
                        email.setTo(header.getValue());
                        break;
                    case "Subject":
                        email.setSubject(header.getValue());
                        break;
                    default:
                        break;
                }
            }
            extractBody(payload, email);
        }
        return email;
    }

    private void extractBody(MessagePart part, LocalEmail email) {
        String mimeType = part.getMimeType();
        if (mimeType == null) return;

        if (mimeType.equals("text/plain") && part.getBody() != null && part.getBody().getData() != null) {
            email.setBodyText(decodeBase64(part.getBody().getData()));
        } else if (mimeType.equals("text/html") && part.getBody() != null && part.getBody().getData() != null) {
            email.setBodyHtml(decodeBase64(part.getBody().getData()));
        } else if (mimeType.startsWith("multipart/")) {
            for (MessagePart subPart : safeList(part.getParts())) {
                extractBody(subPart, email);
            }
        }
    }

    private String decodeBase64(String data) {
        try {
            return new String(Base64.getUrlDecoder().decode(data));
        } catch (Exception e) {
            return "";
        }
    }

    private <T> List<T> safeList(List<T> list) {
        return list != null ? list : Collections.emptyList();
    }
}
