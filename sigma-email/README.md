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
