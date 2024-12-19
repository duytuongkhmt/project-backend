package project.model.response;

import java.util.LinkedHashMap;
import java.util.Map;

public record ErrorResponse(Map<String, Object> errors) {
    public static ErrorResponse errorMessageAndCause(Object message, Object cause) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("message", message);
        error.put("cause", cause);
        return new ErrorResponse(error);
    }
}
