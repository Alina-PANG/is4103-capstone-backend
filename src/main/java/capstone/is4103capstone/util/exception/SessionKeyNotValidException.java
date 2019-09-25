package capstone.is4103capstone.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SessionKeyNotValidException extends RuntimeException {

    public SessionKeyNotValidException() {
    }

    public SessionKeyNotValidException(String message) {
        super(message);
    }

    public SessionKeyNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}