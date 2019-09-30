package capstone.is4103capstone.util.exception;

public class SeatAllocationException extends RuntimeException {
    public SeatAllocationException(String message) {super(message);}

    public SeatAllocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
