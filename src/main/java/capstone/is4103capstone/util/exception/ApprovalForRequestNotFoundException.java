package capstone.is4103capstone.util.exception;

public class ApprovalForRequestNotFoundException extends RuntimeException {
    public ApprovalForRequestNotFoundException(String message) {
        super(message);
    }

    public ApprovalForRequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
