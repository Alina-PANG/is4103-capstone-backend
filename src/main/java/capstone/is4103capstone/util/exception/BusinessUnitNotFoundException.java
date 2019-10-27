package capstone.is4103capstone.util.exception;

public class BusinessUnitNotFoundException extends RuntimeException {
    public BusinessUnitNotFoundException(String message) {
        super(message);
    }

    public BusinessUnitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
