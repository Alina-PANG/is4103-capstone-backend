package capstone.is4103capstone.util.exception;

public class SeatMapCreationException extends RuntimeException {
    public SeatMapCreationException(String message) {super(message);}

    public SeatMapCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
