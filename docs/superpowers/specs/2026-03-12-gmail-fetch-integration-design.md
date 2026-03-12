# Gmail Fetch Integration Design

**Date:** 2026-03-12
**Module:** sigma-email
**Branch:** feature/gmail-fetch

---

## Summary

Add Google Gmail API integration to the `sigma-email` module. Every 5 minutes, fetch unread emails from a specified Gmail account via OAuth2, store each email as a JSON file locally, and mark them as read.

---

## Architecture

New package `com.sigma.email.gmail` added to `sigma-email` module:

```
sigma-email/
├── src/main/java/com/sigma/email/
│   ├── EmailSender.java              (existing)
│   └── gmail/
│       ├── GmailOAuth2Config.java    # Build Gmail API client via credentials.json
│       ├── GmailFetcher.java         # Fetch unread emails, mark as read
│       ├── EmailStorageService.java  # Write email as JSON file to local disk
│       └── GmailScheduler.java       # @Scheduled every 5 minutes
└── src/main/resources/
    ├── application.yml               (add gmail config section)
    └── credentials.json              # Google OAuth2 client secrets (gitignored)
```

**Data flow:**
```
GmailScheduler (@Scheduled, every 5 min)
  → GmailFetcher.fetchUnread()
      → Gmail API: search query "is:unread in:inbox"
      → For each message: get full details (from, to, subject, body)
      → Gmail API: remove UNREAD label (mark as read)
  → EmailStorageService.save(email)
      → Write to {storageDir}/{yyyy-MM-dd}/{messageId}.json
```

---

## Data Model

**LocalEmail.java (DTO):**
```java
String messageId
String from
String to
String subject
Instant receivedAt
String bodyText
String bodyHtml
Instant fetchedAt
```

**JSON file path:** `{storageDir}/{yyyy-MM-dd}/{messageId}.json`

**JSON file example:**
```json
{
  "messageId": "18e3a2b1c4d5f6a7",
  "from": "sender@example.com",
  "to": "your@gmail.com",
  "subject": "Hello World",
  "receivedAt": "2026-03-12T10:30:00Z",
  "bodyText": "Plain text content",
  "bodyHtml": "<p>HTML content</p>",
  "fetchedAt": "2026-03-12T10:35:00Z"
}
```

---

## Configuration (application.yml)

```yaml
sigma:
  gmail:
    credentials-path: classpath:credentials.json
    tokens-dir: /tmp/sigma-gmail-tokens
    target-email: your@gmail.com
    storage-dir: /tmp/sigma-gmail-emails
    fetch-interval-ms: 300000
```

---

## Dependencies (pom.xml additions)

- `com.google.apis:google-api-services-gmail:v1-rev20220404-2.0.0`
- `com.google.oauth-client:google-oauth-client-jetty:1.34.1`
- `com.google.http-client:google-http-client-jackson2:1.43.3`

---

## Error Handling

- OAuth2 token refresh handled automatically by Google SDK
- If Gmail API call fails: log error, skip this cycle, retry next scheduled run
- If file write fails: log error with messageId, do not mark email as read (so it will be retried)
- Duplicate prevention: if `{messageId}.json` already exists, skip writing

---

## Testing

- Unit test `GmailFetcher` with mocked Gmail API client
- Unit test `EmailStorageService` with temp directory
- Integration test requires real credentials (excluded from CI via `@Tag("integration")`)
