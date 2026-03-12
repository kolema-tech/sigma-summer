# Gmail Fetch Integration Implementation Plan

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add Gmail OAuth2 integration to the `sigma-email` module that polls for unread emails every 5 minutes and stores each as a JSON file locally.

**Architecture:** A new `gmail` sub-package inside `sigma-email` contains four focused classes: OAuth2 config (builds the Gmail API client), a fetcher (parses unread emails), a storage service (writes JSON files), and a scheduler (triggers every 5 minutes and coordinates fetch → save → mark-as-read in that order to ensure retry on save failure). Spring Boot auto-configuration wires them together via `@Configuration` and `@Scheduled`.

**Tech Stack:** Google Gmail API Java SDK (`google-api-services-gmail`), Google OAuth2 client (`google-oauth-client-jetty` — for developer machine first-time auth only, not CI), Jackson for JSON serialization, Spring Boot `@Scheduled`, Maven.

---

## Chunk 1: Project Setup & Dependencies

### Task 1: Create feature branch

**Files:**
- No file changes — git only

- [ ] **Step 1: Verify branch does not already exist**

```bash
cd /Users/hustonpeng/github/sigma-summer
git branch --list feature/gmail-fetch
```

Expected: empty output (no existing branch). If the branch already exists, skip Step 2 and check it out with `git checkout feature/gmail-fetch`.

- [ ] **Step 2: Create and switch to new branch**

```bash
git checkout -b feature/gmail-fetch
```

Expected: `Switched to a new branch 'feature/gmail-fetch'`

- [ ] **Step 3: Verify branch is active**

```bash
git branch
```

Expected: `* feature/gmail-fetch` is active

---

### Task 2: Add Maven dependencies to sigma-email

**Files:**
- Modify: `sigma-email/pom.xml`

> **Note:** `google-oauth-client-jetty` opens a local browser for first-time OAuth2 authorization. This only runs on a developer's machine — it will fail in headless CI environments. Integration tests requiring real credentials must be excluded from CI (see Task 6).

- [ ] **Step 1: Add Google API dependencies**

In `sigma-email/pom.xml`, inside `<dependencies>`, add:

```xml
<!-- Google Gmail API (verify latest version at https://mvnrepository.com/artifact/com.google.apis/google-api-services-gmail) -->
<dependency>
    <groupId>com.google.apis</groupId>
    <artifactId>google-api-services-gmail</artifactId>
    <version>v1-rev20220404-2.0.0</version>
</dependency>

<!-- Google OAuth2 client with local server for first-time dev-machine auth -->
<dependency>
    <groupId>com.google.oauth-client</groupId>
    <artifactId>google-oauth-client-jetty</artifactId>
    <version>1.34.1</version>
</dependency>

<!-- HTTP client with Jackson JSON adapter -->
<dependency>
    <groupId>com.google.http-client</groupId>
    <artifactId>google-http-client-jackson2</artifactId>
    <version>1.43.3</version>
</dependency>

<!-- Mockito for unit tests -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>
```

- [ ] **Step 2: Validate pom.xml is well-formed**

```bash
mvn validate -pl sigma-email -q
```

Expected: `BUILD SUCCESS` — if this fails, the XML in Step 1 has a syntax error; check for unclosed tags.

- [ ] **Step 3: Verify dependencies resolve**

```bash
mvn dependency:resolve -pl sigma-email -q
```

Expected: `BUILD SUCCESS` — all four new dependencies downloaded

- [ ] **Step 4: Commit**

```bash
git add sigma-email/pom.xml
git commit -m "feat(sigma-email): add Google Gmail API dependencies"
```

---

## Chunk 2: Core Classes

### Task 3: Create LocalEmail DTO

**Files:**
- Create: `sigma-email/src/main/java/com/sigma/email/gmail/LocalEmail.java`
- Create: `sigma-email/src/test/java/com/sigma/email/gmail/LocalEmailTest.java`

- [ ] **Step 1: Write the failing test**

Create `sigma-email/src/test/java/com/sigma/email/gmail/LocalEmailTest.java`:

```java
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
```

- [ ] **Step 2: Run test to verify it fails**

```bash
mvn test -pl sigma-email -Dtest=LocalEmailTest -q
```

Expected: FAIL — `LocalEmail` class not found

- [ ] **Step 3: Create LocalEmail.java**

```java
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
```

- [ ] **Step 4: Run test to verify it passes**

```bash
mvn test -pl sigma-email -Dtest=LocalEmailTest -q
```

Expected: BUILD SUCCESS, 1 test passed

- [ ] **Step 5: Commit**

```bash
git add sigma-email/src/main/java/com/sigma/email/gmail/LocalEmail.java \
        sigma-email/src/test/java/com/sigma/email/gmail/LocalEmailTest.java
git commit -m "feat(sigma-email): add LocalEmail DTO"
```

---

### Task 4: Create EmailStorageService

**Files:**
- Create: `sigma-email/src/main/java/com/sigma/email/gmail/EmailStorageService.java`
- Create: `sigma-email/src/test/java/com/sigma/email/gmail/EmailStorageServiceTest.java`

- [ ] **Step 1: Write the failing test**

Create `sigma-email/src/test/java/com/sigma/email/gmail/EmailStorageServiceTest.java`:

```java
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
```

- [ ] **Step 2: Run test to verify it fails**

```bash
mvn test -pl sigma-email -Dtest=EmailStorageServiceTest -q
```

Expected: FAIL — `EmailStorageService` not found

- [ ] **Step 3: Create EmailStorageService.java**

> **Note:** `save()` returns `boolean` — `true` if the file was written, `false` if skipped (duplicate) or on error. The scheduler uses this return value to decide whether to mark the email as read.

```java
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
```

- [ ] **Step 4: Run tests to verify they pass**

```bash
mvn test -pl sigma-email -Dtest=EmailStorageServiceTest -q
```

Expected: BUILD SUCCESS, 3 tests passed

- [ ] **Step 5: Commit**

```bash
git add sigma-email/src/main/java/com/sigma/email/gmail/EmailStorageService.java \
        sigma-email/src/test/java/com/sigma/email/gmail/EmailStorageServiceTest.java
git commit -m "feat(sigma-email): add EmailStorageService - writes email JSON to disk, returns save status"
```

---

### Task 5: Create GmailOAuth2Config

**Files:**
- Create: `sigma-email/src/main/java/com/sigma/email/gmail/GmailOAuth2Config.java`
- Create: `sigma-email/src/main/resources/credentials.json.example` (safe template to commit)

- [ ] **Step 1: Create credentials.json.example (safe to commit)**

Create `sigma-email/src/main/resources/credentials.json.example`:

```json
{
  "_note": "Copy this file to credentials.json and replace placeholder values with real Google OAuth2 credentials from Google Cloud Console. credentials.json is gitignored.",
  "installed": {
    "client_id": "YOUR_CLIENT_ID.apps.googleusercontent.com",
    "client_secret": "YOUR_CLIENT_SECRET",
    "redirect_uris": ["urn:ietf:wg:oauth:2.0:oob", "http://localhost"],
    "auth_uri": "https://accounts.google.com/o/oauth2/auth",
    "token_uri": "https://oauth2.googleapis.com/token"
  }
}
```

- [ ] **Step 2: Gitignore the real credentials.json**

Check if root `.gitignore` exists:

```bash
cat /Users/hustonpeng/github/sigma-summer/.gitignore
```

Add the following lines if not already present (edit the file with your editor):

```
# Google OAuth2 credentials - never commit real credentials
sigma-email/src/main/resources/credentials.json
/tmp/sigma-gmail-tokens/
```

- [ ] **Step 3: Verify gitignore is active**

```bash
cd /Users/hustonpeng/github/sigma-summer
touch sigma-email/src/main/resources/credentials.json
git check-ignore -v sigma-email/src/main/resources/credentials.json
```

Expected: output like `.gitignore:N:sigma-email/src/main/resources/credentials.json` confirming the file is ignored. Then remove the test file:

```bash
rm sigma-email/src/main/resources/credentials.json
```

- [ ] **Step 4: Create GmailOAuth2Config.java**

```java
package com.sigma.email.gmail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
@Configuration
public class GmailOAuth2Config {

    private static final String APPLICATION_NAME = "sigma-email-gmail-fetch";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${sigma.gmail.credentials-path}")
    private Resource credentialsResource;

    @Value("${sigma.gmail.tokens-dir}")
    private String tokensDir;

    @Bean
    public Gmail gmailClient() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);
        return new Gmail.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Bean
    public ObjectMapper emailObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    private Credential authorize(NetHttpTransport httpTransport) throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY,
                new InputStreamReader(credentialsResource.getInputStream(), StandardCharsets.UTF_8)
        );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport,
                JSON_FACTORY,
                clientSecrets,
                Collections.singletonList(GmailScopes.GMAIL_MODIFY)
        )
                .setDataStoreFactory(new FileDataStoreFactory(new File(tokensDir)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
```

- [ ] **Step 5: Verify the class compiles**

```bash
mvn compile -pl sigma-email -q
```

Expected: `BUILD SUCCESS` — GmailOAuth2Config compiles without errors

- [ ] **Step 6: Commit**

```bash
git add sigma-email/src/main/java/com/sigma/email/gmail/GmailOAuth2Config.java \
        sigma-email/src/main/resources/credentials.json.example \
        .gitignore
git commit -m "feat(sigma-email): add GmailOAuth2Config - builds Gmail API client via OAuth2"
```

---

### Task 6: Create GmailFetcher

**Files:**
- Create: `sigma-email/src/main/java/com/sigma/email/gmail/GmailFetcher.java`
- Create: `sigma-email/src/test/java/com/sigma/email/gmail/GmailFetcherTest.java`

> **Design note:** `GmailFetcher.fetchUnread()` parses and returns emails WITHOUT marking them as read. Marking as read is done by `GmailFetcher.markAsRead(messageId)`, called by `GmailScheduler` only after a successful `EmailStorageService.save()`. This ensures a failed save will be retried on the next poll cycle.

- [ ] **Step 1: Write the failing tests**

Create `sigma-email/src/test/java/com/sigma/email/gmail/GmailFetcherTest.java`:

```java
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
        // fetchUnread does NOT call modify — mark-as-read is the scheduler's responsibility
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
```

- [ ] **Step 2: Run test to verify it fails**

```bash
mvn test -pl sigma-email -Dtest=GmailFetcherTest -q
```

Expected: FAIL — `GmailFetcher` not found

- [ ] **Step 3: Create GmailFetcher.java**

```java
package com.sigma.email.gmail;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Slf4j
public class GmailFetcher {

    private final Gmail gmail;
    private final String targetEmail;

    public GmailFetcher(Gmail gmail, String targetEmail) {
        this.gmail = gmail;
        this.targetEmail = targetEmail;
    }

    /**
     * Fetches unread emails from the inbox. Does NOT mark them as read.
     * The caller (GmailScheduler) is responsible for calling markAsRead()
     * after successfully saving each email to disk.
     */
    public List<LocalEmail> fetchUnread() {
        try {
            ListMessagesResponse response = gmail.users().messages()
                    .list(targetEmail)
                    .setQ("is:unread in:inbox")
                    .execute();

            if (response.getMessages() == null || response.getMessages().isEmpty()) {
                log.debug("No unread emails found");
                return Collections.emptyList();
            }

            List<LocalEmail> results = new ArrayList<>();
            for (Message msgRef : response.getMessages()) {
                try {
                    Message full = gmail.users().messages()
                            .get(targetEmail, msgRef.getId())
                            .setFormat("full")
                            .execute();
                    results.add(parseEmail(full));
                } catch (Exception e) {
                    log.error("Failed to fetch message {}: {}", msgRef.getId(), e.getMessage(), e);
                }
            }
            return results;
        } catch (Exception e) {
            log.error("Failed to fetch unread emails: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * Marks a single email as read. Called by GmailScheduler only after
     * EmailStorageService.save() returns true, so failed saves are retried.
     */
    public void markAsRead(String messageId) {
        try {
            ModifyMessageRequest request = new ModifyMessageRequest()
                    .setRemoveLabelIds(Collections.singletonList("UNREAD"));
            gmail.users().messages().modify(targetEmail, messageId, request).execute();
            log.debug("Marked message {} as read", messageId);
        } catch (Exception e) {
            log.warn("Failed to mark message {} as read: {}", messageId, e.getMessage());
        }
    }

    private LocalEmail parseEmail(Message message) {
        LocalEmail email = new LocalEmail();
        email.setMessageId(message.getId());
        email.setFetchedAt(Instant.now());

        if (message.getInternalDate() != null) {
            email.setReceivedAt(Instant.ofEpochMilli(message.getInternalDate()));
        }

        MessagePart payload = message.getPayload();
        if (payload != null) {
            for (MessagePartHeader header : safeList(payload.getHeaders())) {
                switch (header.getName()) {
                    case "From" -> email.setFrom(header.getValue());
                    case "To" -> email.setTo(header.getValue());
                    case "Subject" -> email.setSubject(header.getValue());
                }
            }
            extractBody(payload, email);
        }
        return email;
    }

    private void extractBody(MessagePart part, LocalEmail email) {
        String mimeType = part.getMimeType();
        if (mimeType == null) return;

        if (mimeType.equals("text/plain") && part.getBody() != null && part.getBody().getData() != null) {
            email.setBodyText(decodeBase64(part.getBody().getData()));
        } else if (mimeType.equals("text/html") && part.getBody() != null && part.getBody().getData() != null) {
            email.setBodyHtml(decodeBase64(part.getBody().getData()));
        } else if (mimeType.startsWith("multipart/")) {
            for (MessagePart subPart : safeList(part.getParts())) {
                extractBody(subPart, email);
            }
        }
    }

    private String decodeBase64(String data) {
        try {
            return new String(Base64.getUrlDecoder().decode(data));
        } catch (Exception e) {
            return "";
        }
    }

    private <T> List<T> safeList(List<T> list) {
        return list != null ? list : Collections.emptyList();
    }
}
```

- [ ] **Step 4: Run tests to verify they pass**

```bash
mvn test -pl sigma-email -Dtest=GmailFetcherTest -q
```

Expected: BUILD SUCCESS, 3 tests passed

- [ ] **Step 5: Commit**

```bash
git add sigma-email/src/main/java/com/sigma/email/gmail/GmailFetcher.java \
        sigma-email/src/test/java/com/sigma/email/gmail/GmailFetcherTest.java
git commit -m "feat(sigma-email): add GmailFetcher - parses unread emails, exposes markAsRead separately"
```

---

## Chunk 3: Wiring & Configuration

### Task 7: Create GmailScheduler

**Files:**
- Create: `sigma-email/src/main/java/com/sigma/email/gmail/GmailScheduler.java`
- Create: `sigma-email/src/test/java/com/sigma/email/gmail/GmailSchedulerTest.java`

- [ ] **Step 1: Write the failing test**

Create `sigma-email/src/test/java/com/sigma/email/gmail/GmailSchedulerTest.java`:

```java
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
        when(emailStorageService.save(email)).thenReturn(true); // save succeeded

        scheduler.fetchAndStore();

        verify(gmailFetcher).markAsRead("msg001"); // mark as read only because save returned true
    }

    @Test
    void shouldNotMarkAsReadWhenSaveFails() {
        LocalEmail email = new LocalEmail();
        email.setMessageId("msg002");
        email.setFetchedAt(Instant.now());
        email.setReceivedAt(Instant.now());

        when(gmailFetcher.fetchUnread()).thenReturn(List.of(email));
        when(emailStorageService.save(email)).thenReturn(false); // save failed

        scheduler.fetchAndStore();

        verify(gmailFetcher, never()).markAsRead(any()); // NOT marked as read — will retry next cycle
    }

    @Test
    void shouldHandleEmptyFetchGracefully() {
        when(gmailFetcher.fetchUnread()).thenReturn(List.of());

        scheduler.fetchAndStore();

        verify(emailStorageService, never()).save(any());
        verify(gmailFetcher, never()).markAsRead(any());
    }
}
```

- [ ] **Step 2: Run test to verify it fails**

```bash
mvn test -pl sigma-email -Dtest=GmailSchedulerTest -q
```

Expected: FAIL — `GmailScheduler` not found

- [ ] **Step 3: Create GmailScheduler.java**

```java
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
                // Mark as read only after confirmed save — ensures retry on save failure
                gmailFetcher.markAsRead(email.getMessageId());
            } else {
                log.warn("Skipping mark-as-read for {} due to save failure or duplicate",
                        email.getMessageId());
            }
        }

        log.info("Gmail fetch cycle complete");
    }
}
```

- [ ] **Step 4: Run tests to verify they pass**

```bash
mvn test -pl sigma-email -Dtest=GmailSchedulerTest -q
```

Expected: BUILD SUCCESS, 3 tests passed

- [ ] **Step 5: Commit**

```bash
git add sigma-email/src/main/java/com/sigma/email/gmail/GmailScheduler.java \
        sigma-email/src/test/java/com/sigma/email/gmail/GmailSchedulerTest.java
git commit -m "feat(sigma-email): add GmailScheduler - marks as read only after successful save"
```

---

### Task 8: Create GmailFetchAutoConfiguration

**Files:**
- Create: `sigma-email/src/main/java/com/sigma/email/gmail/GmailFetchAutoConfiguration.java`
- Create: `sigma-email/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` OR `META-INF/spring.factories` (depends on Spring Boot version — see Step 1)

- [ ] **Step 1: Determine Spring Boot version**

```bash
grep -A3 "spring-boot-starter-parent\|spring-boot-dependencies" /Users/hustonpeng/github/sigma-summer/pom.xml | grep "<version>"
```

Expected output example: `<version>2.7.5</version>` or `<version>3.1.0</version>`

Record this version — you will use it in Step 3:
- If version is **>= 2.7**: use `AutoConfiguration.imports` file
- If version is **< 2.7**: use `spring.factories` file

- [ ] **Step 2: Create GmailFetchAutoConfiguration.java**

```java
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
```

- [ ] **Step 3: Register auto-configuration (use the version from Step 1)**

**If Spring Boot >= 2.7:** Create `sigma-email/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`:

```
com.sigma.email.gmail.GmailFetchAutoConfiguration
```

**If Spring Boot < 2.7:** Create `sigma-email/src/main/resources/META-INF/spring.factories`:

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.sigma.email.gmail.GmailFetchAutoConfiguration
```

- [ ] **Step 4: Verify the configuration class compiles**

```bash
mvn compile -pl sigma-email -q
```

Expected: `BUILD SUCCESS`

- [ ] **Step 5: Commit**

```bash
git add sigma-email/src/main/java/com/sigma/email/gmail/GmailFetchAutoConfiguration.java \
        sigma-email/src/main/resources/META-INF/
git commit -m "feat(sigma-email): add auto-configuration for Gmail fetch"
```

---

### Task 9: Update application.yml with Gmail config

**Files:**
- Modify: `sigma-email/src/main/resources/application.yml`

- [ ] **Step 1: Add Gmail config section**

Append to `sigma-email/src/main/resources/application.yml`:

```yaml
sigma:
  gmail:
    credentials-path: classpath:credentials.json
    tokens-dir: ${GMAIL_TOKENS_DIR:/tmp/sigma-gmail-tokens}
    target-email: ${GMAIL_TARGET_EMAIL:your@gmail.com}
    storage-dir: ${GMAIL_STORAGE_DIR:/tmp/sigma-gmail-emails}
    fetch-interval-ms: ${GMAIL_FETCH_INTERVAL_MS:300000}
```

> All five properties support environment variable overrides via `${ENV_VAR:default}` — recommended for production deployments.

- [ ] **Step 2: Commit**

```bash
git add sigma-email/src/main/resources/application.yml
git commit -m "feat(sigma-email): add Gmail fetch configuration to application.yml"
```

---

### Task 10: Write README with setup instructions

**Files:**
- Create: `sigma-email/README.md`

- [ ] **Step 1: Create README.md**

Create `sigma-email/README.md` with the following content (note: the JSON example uses a fenced code block inside the markdown):

````markdown
# sigma-email

Spring Boot module for email sending (via JavaMailSender + Freemarker) and Gmail inbox fetching.

## Gmail Fetch Integration

Polls a Gmail inbox every 5 minutes for unread emails and stores each as a JSON file on disk.

### Prerequisites

1. **Google Cloud Console Setup:**
   - Create a project at https://console.cloud.google.com
   - Enable the Gmail API for the project
   - Create OAuth2 credentials (choose "Desktop App" type)
   - Download `credentials.json`

2. **Place credentials:** Copy `credentials.json` to `sigma-email/src/main/resources/`
   - A safe template is provided at `src/main/resources/credentials.json.example`
   - The real `credentials.json` is gitignored — never commit it

### Configuration

All properties support environment variable overrides:

| Property | Environment Variable | Default | Description |
|----------|---------------------|---------|-------------|
| `sigma.gmail.target-email` | `GMAIL_TARGET_EMAIL` | `your@gmail.com` | Gmail address to monitor |
| `sigma.gmail.storage-dir` | `GMAIL_STORAGE_DIR` | `/tmp/sigma-gmail-emails` | Local directory for JSON files |
| `sigma.gmail.tokens-dir` | `GMAIL_TOKENS_DIR` | `/tmp/sigma-gmail-tokens` | OAuth2 token cache directory |
| `sigma.gmail.credentials-path` | — | `classpath:credentials.json` | Path to OAuth2 credentials file |
| `sigma.gmail.fetch-interval-ms` | `GMAIL_FETCH_INTERVAL_MS` | `300000` | Poll interval in ms (default 5 min) |

### First-Time OAuth2 Authorization

On first run, a browser window opens for Google sign-in. After granting access, the token is cached in `tokens-dir` and reused automatically on subsequent runs.

### Output Format

Each email is saved as `{storage-dir}/{yyyy-MM-dd}/{messageId}.json`:

```json
{
  "messageId": "18e3a2b1c4d5f6a7",
  "from": "sender@example.com",
  "to": "your@gmail.com",
  "subject": "Hello",
  "receivedAt": "2026-03-12T10:00:00Z",
  "bodyText": "Plain text content",
  "bodyHtml": "<p>HTML content</p>",
  "fetchedAt": "2026-03-12T10:05:00Z"
}
```

### Retry Behavior

If saving an email to disk fails, the email is **not** marked as read in Gmail. It will be picked up again on the next poll cycle.
````

- [ ] **Step 2: Commit**

```bash
git add sigma-email/README.md
git commit -m "docs(sigma-email): add Gmail fetch setup instructions"
```
