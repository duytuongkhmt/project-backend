package project.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
        super("Cannot find data", new Throwable("Input data not exist!"));
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, String cause) {
        super(message, new Throwable(cause));
    }
}
