package capstone.is4103capstone.util.exception;

public class CompanyFunctionNotFoundException extends RuntimeException {
    public CompanyFunctionNotFoundException(String message) {
        super(message);
    }

    public CompanyFunctionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
