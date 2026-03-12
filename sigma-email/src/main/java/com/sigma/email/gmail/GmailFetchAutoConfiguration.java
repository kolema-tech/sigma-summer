package com.sigma.email.gmail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.gmail.Gmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "sigma.gmail", name = "target-email")
@Import(GmailOAuth2Config.class)
public class GmailFetchAutoConfiguration {

    @Value("${sigma.gmail.target-email}")
    private String targetEmail;

    @Value("${sigma.gmail.storage-dir}")
    private String storageDir;

    @Bean
    public GmailFetcher gmailFetcher(Gmail gmail) {
        return new GmailFetcher(gmail, targetEmail);
    }

    @Bean
    public EmailStorageService emailStorageService(ObjectMapper emailObjectMapper) {
        return new EmailStorageService(storageDir, emailObjectMapper);
    }
}
