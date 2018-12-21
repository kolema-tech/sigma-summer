```java
public class SigmaException extends RuntimeException {
    private static final long serialVersionUID = -8541311111016065562L;

    public SigmaException(String message) {
        super(message);
    }

    public SigmaException(String message, Throwable cause) {
        super(message, cause);
    }

    public SigmaException(Throwable cause) {
        super(cause);
    }

    protected SigmaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```