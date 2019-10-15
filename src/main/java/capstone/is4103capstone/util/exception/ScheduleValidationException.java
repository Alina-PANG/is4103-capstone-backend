package capstone.is4103capstone.util.exception;

public class ScheduleValidationException extends RuntimeException {
    public ScheduleValidationException(String message) {super(message);}

    public ScheduleValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
