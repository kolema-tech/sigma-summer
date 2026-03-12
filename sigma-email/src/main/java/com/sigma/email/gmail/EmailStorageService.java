package com.sigma.email.gmail;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
public class EmailStorageService {

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

    private final String storageDir;
    private final ObjectMapper objectMapper;

    public EmailStorageService(String storageDir, ObjectMapper objectMapper) {
        this.storageDir = storageDir;
        this.objectMapper = objectMapper;
    }

    /**
     * Saves the email to disk.
     * @return true if written successfully, false if already exists or on error.
     *         The scheduler only marks an email as read when this returns true.
     */
    public boolean save(LocalEmail email) {
        try {
            String dateDir = DATE_FMT.format(email.getFetchedAt());
            File dir = new File(storageDir, dateDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, email.getMessageId() + ".json");
            if (file.exists()) {
                log.debug("Email {} already saved, skipping", email.getMessageId());
                return false;
            }

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, email);
            log.info("Saved email {} to {}", email.getMessageId(), file.getAbsolutePath());
            return true;
        } catch (Exception e) {
            log.error("Failed to save email {}: {}", email.getMessageId(), e.getMessage(), e);
            return false;
        }
    }
}
