package com.sigma.email.gmail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GmailSchedulerTest {

    @Mock GmailFetcher gmailFetcher;
    @Mock EmailStorageService emailStorageService;

    @InjectMocks GmailScheduler scheduler;

    @Test
    void shouldMarkAsReadOnlyAfterSuccessfulSave() {
        LocalEmail email = new LocalEmail();
        email.setMessageId("msg001");
        email.setFetchedAt(Instant.now());
        email.setReceivedAt(Instant.now());

        when(gmailFetcher.fetchUnread()).thenReturn(List.of(email));
        when(emailStorageService.save(email)).thenReturn(true);

        scheduler.fetchAndStore();

        verify(gmailFetcher).markAsRead("msg001");
    }

    @Test
    void shouldNotMarkAsReadWhenSaveFails() {
        LocalEmail email = new LocalEmail();
        email.setMessageId("msg002");
        email.setFetchedAt(Instant.now());
        email.setReceivedAt(Instant.now());

        when(gmailFetcher.fetchUnread()).thenReturn(List.of(email));
        when(emailStorageService.save(email)).thenReturn(false);

        scheduler.fetchAndStore();

        verify(gmailFetcher, never()).markAsRead(any());
    }

    @Test
    void shouldHandleEmptyFetchGracefully() {
        when(gmailFetcher.fetchUnread()).thenReturn(List.of());

        scheduler.fetchAndStore();

        verify(emailStorageService, never()).save(any());
        verify(gmailFetcher, never()).markAsRead(any());
    }
}
