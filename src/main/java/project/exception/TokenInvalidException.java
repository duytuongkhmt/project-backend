package project.exception;

public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException() {
        super("Cannot get id token from google");
    }

    public TokenInvalidException(String message) {
        super(message);
    }
}
