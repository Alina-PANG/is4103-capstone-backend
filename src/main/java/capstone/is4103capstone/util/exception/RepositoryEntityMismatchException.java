package capstone.is4103capstone.util.exception;

public class RepositoryEntityMismatchException  extends RuntimeException {
    public RepositoryEntityMismatchException(String message) {super(message);}
    public RepositoryEntityMismatchException(String message, Throwable cause) {super(message, cause);}
}
