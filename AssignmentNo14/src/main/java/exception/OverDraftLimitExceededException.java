package exception;

public class OverDraftLimitExceededException extends RuntimeException {
    public OverDraftLimitExceededException(String message) {
        super(message);
    }

    public OverDraftLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}