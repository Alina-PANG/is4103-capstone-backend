package capstone.is4103capstone.util.exception;

public class SeatMapUpdateException extends RuntimeException {
    public SeatMapUpdateException(String message) {super(message);}

    public SeatMapUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
