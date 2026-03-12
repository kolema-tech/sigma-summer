package com.sigma.email.gmail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EmailStorageServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldWriteEmailJsonFile() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        EmailStorageService service = new EmailStorageService(tempDir.toString(), mapper);

        LocalEmail email = new LocalEmail();
        email.setMessageId("msg001");
        email.setFrom("a@example.com");
        email.setTo("b@gmail.com");
        email.setSubject("Hello");
        email.setReceivedAt(Instant.parse("2026-03-12T10:00:00Z"));
        email.setBodyText("plain text");
        email.setBodyHtml("<p>html</p>");
        email.setFetchedAt(Instant.parse("2026-03-12T10:05:00Z"));

        service.save(email);

        // Verify file exists under date subdirectory
        File[] dateDirs = tempDir.toFile().listFiles(File::isDirectory);
        assertNotNull(dateDirs);
        assertEquals(1, dateDirs.length);

        File[] jsonFiles = dateDirs[0].listFiles();
        assertNotNull(jsonFiles);
        assertEquals(1, jsonFiles.length);
        assertTrue(jsonFiles[0].getName().endsWith(".json"));

        // Verify content
        LocalEmail saved = mapper.readValue(jsonFiles[0], LocalEmail.class);
        assertEquals("msg001", saved.getMessageId());
        assertEquals("Hello", saved.getSubject());
    }

    @Test
    void shouldSkipIfFileAlreadyExists() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        EmailStorageService service = new EmailStorageService(tempDir.toString(), mapper);

        LocalEmail email = new LocalEmail();
        email.setMessageId("msg002");
        email.setFetchedAt(Instant.now());
        email.setReceivedAt(Instant.now());

        service.save(email);
        service.save(email); // second call should be no-op

        File[] dateDirs = tempDir.toFile().listFiles(File::isDirectory);
        assertNotNull(dateDirs);
        File[] jsonFiles = dateDirs[0].listFiles();
        assertNotNull(jsonFiles);
        assertEquals(1, jsonFiles.length); // still only 1 file
    }

    @Test
    void shouldReturnTrueOnSuccessAndFalseOnDuplicate() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        EmailStorageService service = new EmailStorageService(tempDir.toString(), mapper);

        LocalEmail email = new LocalEmail();
        email.setMessageId("msg003");
        email.setFetchedAt(Instant.now());
        email.setReceivedAt(Instant.now());

        assertTrue(service.save(email));   // first save returns true
        assertFalse(service.save(email));  // duplicate returns false
    }
}
