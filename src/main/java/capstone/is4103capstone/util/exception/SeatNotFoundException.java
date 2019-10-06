package capstone.is4103capstone.util.exception;

public class SeatNotFoundException extends RuntimeException{
    public SeatNotFoundException(String message) {super(message);}

    public SeatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
