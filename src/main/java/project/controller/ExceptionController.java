package project.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ResponseStatusException;
import project.exception.*;
import project.model.response.ErrorResponse;
import project.payload.response.ResponseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @Value("${spring.servlet.multipart.max-file-size}")
    String size;

    @ExceptionHandler(value = TokenInvalidException.class)
    public ResponseEntity<Object> exception(TokenInvalidException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<String> handleAuthenticationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed! " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);
        ResponseObject response = new ResponseObject(result, HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleExceptionUnprocessable(ResponseStatusException ex) {
        if (ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getReason());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal exception. " +
                "Message: " + ex.getMessage() + ". " +
                "Cause: " + ex.getCause() + ". " +
                "Class: " + ex.getClass());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleCustomAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ErrorResponse.errorMessageAndCause(ex.getMessage(), ex.getCause().getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("Exception : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal exception. " +
                "Message: " + ex.getMessage() + ". " +
                "Cause: " + ex.getCause() + ". " +
                "Class: " + ex.getClass());
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleFileSizeException(
            MaxUploadSizeExceededException ex
    ) {
        String cause = "File too big, size file has less than or equal " + size;
        return new ResponseEntity<>(ErrorResponse.errorMessageAndCause(ex.getMessage(), cause), HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> handleMultipartException(MultipartException ex) {
        return new ResponseEntity<>(ErrorResponse.errorMessageAndCause(ex.getMessage(), ex.getCause().getMessage()), HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            DataNotFoundException ex
    ) {
        return new ResponseEntity<>(ErrorResponse.errorMessageAndCause(ex.getMessage(), ex.getCause().getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(
            InvalidDataException ex
    ) {
        return new ResponseEntity<>(ErrorResponse.errorMessageAndCause(ex.getMessage(), ex.getCause().getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(
            BindException ex
    ) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            errorMessages.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(ErrorResponse.errorMessageAndCause(errorMessages, "Validate errors"), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(ImportException.class)
    public ResponseEntity<ErrorResponse> handleImportException(ImportException ex) {
        return new ResponseEntity<>(ErrorResponse.errorMessageAndCause(ex.getMessage(), ex.getCause().getMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponse> handleSystemException(
            SystemException ex
    ) {
        return new ResponseEntity<>(ErrorResponse.errorMessageAndCause(ex.getMessage(), ex.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
