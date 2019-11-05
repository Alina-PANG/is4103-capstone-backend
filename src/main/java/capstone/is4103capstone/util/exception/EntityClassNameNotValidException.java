package capstone.is4103capstone.util.exception;

public class EntityClassNameNotValidException extends RuntimeException {
    public EntityClassNameNotValidException(String message) {
        super(message);
    }

    public EntityClassNameNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

}
