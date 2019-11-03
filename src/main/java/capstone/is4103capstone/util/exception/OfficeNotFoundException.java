package capstone.is4103capstone.util.exception;

public class OfficeNotFoundException extends RuntimeException {
    public OfficeNotFoundException(String message) {super(message);}
    public OfficeNotFoundException(String message, Throwable cause) {super(message, cause);}
}
