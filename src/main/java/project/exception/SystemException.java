package project.exception;

public class SystemException extends Exception {

    public SystemException() {
        super("An unexpected error occurred", new Throwable("Internal server error!"));
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, String cause) {
        super(message, new Throwable(cause));
    }
}
