package capstone.is4103capstone.util.exception;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String message) {
        super(message);
    }
    public UnauthorizedActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
