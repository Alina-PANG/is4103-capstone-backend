package capstone.is4103capstone.util.exception;

public class CreateSeatAllocationRequestException extends RuntimeException {
    public CreateSeatAllocationRequestException(String message) {
        super(message);
    }

    public CreateSeatAllocationRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
