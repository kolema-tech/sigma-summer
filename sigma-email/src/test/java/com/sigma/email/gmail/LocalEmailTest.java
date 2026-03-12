package com.sigma.email.gmail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LocalEmailTest {

    @Test
    void shouldSerializeToJson() throws Exception {
        LocalEmail email = new LocalEmail();
        email.setMessageId("abc123");
        email.setFrom("sender@example.com");
        email.setTo("me@gmail.com");
        email.setSubject("Test Subject");
        email.setReceivedAt(Instant.parse("2026-03-12T10:00:00Z"));
        email.setBodyText("Hello");
        email.setBodyHtml("<p>Hello</p>");
        email.setFetchedAt(Instant.parse("2026-03-12T10:05:00Z"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(email);

        assertTrue(json.contains("abc123"));
        assertTrue(json.contains("sender@example.com"));
        assertTrue(json.contains("Test Subject"));
    }
}
