package com.sigma.email.kafka;

import com.sigma.email.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEmailConsumer {

    private final EmailSender emailSender;

    @KafkaListener(topics = "${sigma.email.kafka.topic:email-send}", groupId = "${sigma.email.kafka.group-id:sigma-email-group}")
    public void consume(EmailMessage message) {
        try {
            log.info("Received email task: to={}, subject={}", message.getTo(), message.getSubject());
            emailSender.send(message.getFrom(), message.getTo(), message.getSubject(), message.getBody());
            log.info("Email sent to {}", message.getTo());
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", message.getTo(), e.getMessage(), e);
        }
    }
}
