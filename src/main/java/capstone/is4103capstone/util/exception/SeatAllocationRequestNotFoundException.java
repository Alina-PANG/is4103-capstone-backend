package capstone.is4103capstone.util.exception;

public class SeatAllocationRequestNotFoundException extends RuntimeException {
    public SeatAllocationRequestNotFoundException(String message) {super(message);}
    public SeatAllocationRequestNotFoundException(String message, Throwable cause) {super(message, cause);}
}
