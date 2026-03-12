package com.sigma.email.gmail;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GmailFetcherTest {

    @Mock Gmail gmail;
    @Mock Gmail.Users users;
    @Mock Gmail.Users.Messages messages;
    @Mock Gmail.Users.Messages.List listRequest;
    @Mock Gmail.Users.Messages.Get getRequest;
    @Mock Gmail.Users.Messages.Modify modifyRequest;

    GmailFetcher fetcher;

    @BeforeEach
    void setUp() {
        fetcher = new GmailFetcher(gmail, "me@gmail.com");
        when(gmail.users()).thenReturn(users);
        when(users.messages()).thenReturn(messages);
    }

    @Test
    void shouldReturnEmptyListWhenNoUnreadEmails() throws Exception {
        when(messages.list("me@gmail.com")).thenReturn(listRequest);
        when(listRequest.setQ("is:unread in:inbox")).thenReturn(listRequest);
        ListMessagesResponse response = new ListMessagesResponse();
        response.setMessages(null);
        when(listRequest.execute()).thenReturn(response);

        List<LocalEmail> result = fetcher.fetchUnread();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldParseEmailHeadersCorrectly() throws Exception {
        when(messages.list("me@gmail.com")).thenReturn(listRequest);
        when(listRequest.setQ("is:unread in:inbox")).thenReturn(listRequest);
        Message msgRef = new Message().setId("msg123");
        ListMessagesResponse listResponse = new ListMessagesResponse();
        listResponse.setMessages(List.of(msgRef));
        when(listRequest.execute()).thenReturn(listResponse);

        when(messages.get("me@gmail.com", "msg123")).thenReturn(getRequest);
        when(getRequest.setFormat("full")).thenReturn(getRequest);
        Message fullMsg = new Message();
        fullMsg.setId("msg123");
        fullMsg.setInternalDate(1741780800000L);
        MessagePart payload = new MessagePart();
        payload.setMimeType("text/plain");
        payload.setHeaders(List.of(
                new MessagePartHeader().setName("From").setValue("sender@example.com"),
                new MessagePartHeader().setName("To").setValue("me@gmail.com"),
                new MessagePartHeader().setName("Subject").setValue("Test Subject")
        ));
        payload.setParts(null);
        payload.setBody(new MessagePartBody().setData(
                java.util.Base64.getUrlEncoder().encodeToString("plain text".getBytes())
        ));
        fullMsg.setPayload(payload);
        when(getRequest.execute()).thenReturn(fullMsg);

        List<LocalEmail> result = fetcher.fetchUnread();

        assertEquals(1, result.size());
        assertEquals("msg123", result.get(0).getMessageId());
        assertEquals("sender@example.com", result.get(0).getFrom());
        assertEquals("Test Subject", result.get(0).getSubject());
        // fetchUnread does NOT call modify — mark-as-read is the scheduler's job
        verify(messages, never()).modify(any(), any(), any());
    }

    @Test
    void shouldMarkEmailAsReadWhenCalledExplicitly() throws Exception {
        when(messages.modify(eq("me@gmail.com"), eq("msg123"), any())).thenReturn(modifyRequest);
        Message dummy = new Message().setId("msg123");
        when(modifyRequest.execute()).thenReturn(dummy);

        fetcher.markAsRead("msg123");

        verify(messages).modify(eq("me@gmail.com"), eq("msg123"), any());
    }
}
