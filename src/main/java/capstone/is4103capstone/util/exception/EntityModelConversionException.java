package capstone.is4103capstone.util.exception;

public class EntityModelConversionException extends RuntimeException {
    public EntityModelConversionException(String message) {
        super(message);
    }

    public EntityModelConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
