package capstone.is4103capstone.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DbObjectNotFoundException extends RuntimeException {

    public DbObjectNotFoundException() {
    }

    public DbObjectNotFoundException(String message) {
        super(message);
    }

    public DbObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}