package com.sigma.email.kafka;

import lombok.Data;

@Data
public class EmailMessage {
    private String from;
    private String to;
    private String subject;
    private String body;
}
