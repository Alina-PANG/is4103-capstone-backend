package capstone.is4103capstone.util.exception;

public class SeatMapNotFoundException extends RuntimeException{
    public SeatMapNotFoundException(String message) {super(message);}

    public SeatMapNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
