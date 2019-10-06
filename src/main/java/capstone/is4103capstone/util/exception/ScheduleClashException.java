package capstone.is4103capstone.util.exception;

public class ScheduleClashException extends RuntimeException {
    public ScheduleClashException(String message) {super(message);}

    public ScheduleClashException(String message, Throwable cause) {
        super(message, cause);
    }
}
