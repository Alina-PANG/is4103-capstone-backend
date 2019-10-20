package capstone.is4103capstone.util.exception;

public class SeatRequestAdminMatchNotFoundException extends RuntimeException {
    public SeatRequestAdminMatchNotFoundException(String message) {
        super(message);
    }

    public SeatRequestAdminMatchNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
