package capstone.is4103capstone.util.exception;

public class SeatAdminMatchNotFoundException extends RuntimeException {
    public SeatAdminMatchNotFoundException(String message) {super(message);}

    public SeatAdminMatchNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
