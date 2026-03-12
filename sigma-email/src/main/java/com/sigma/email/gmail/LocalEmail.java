package com.sigma.email.gmail;

import lombok.Data;

import java.time.Instant;

@Data
public class LocalEmail {
    private String messageId;
    private String from;
    private String to;
    private String subject;
    private Instant receivedAt;
    private String bodyText;
    private String bodyHtml;
    private Instant fetchedAt;
}
