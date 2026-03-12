package com.sigma.email.gmail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GmailScheduler {

    private final GmailFetcher gmailFetcher;
    private final EmailStorageService emailStorageService;

    @Scheduled(fixedDelayString = "${sigma.gmail.fetch-interval-ms:300000}")
    public void fetchAndStore() {
        log.info("Starting Gmail fetch cycle");
        List<LocalEmail> emails = gmailFetcher.fetchUnread();
        log.info("Fetched {} unread email(s)", emails.size());

        for (LocalEmail email : emails) {
            boolean saved = emailStorageService.save(email);
            if (saved) {
                gmailFetcher.markAsRead(email.getMessageId());
            } else {
                log.warn("Skipping mark-as-read for {} due to save failure or duplicate",
                        email.getMessageId());
            }
        }

        log.info("Gmail fetch cycle complete");
    }
}
