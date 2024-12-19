package project.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {
        super("Invalid data", new Throwable("Invalid data!"));
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, String cause) {
        super(message, new Throwable(cause));
    }
}
