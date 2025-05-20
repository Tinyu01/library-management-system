package com.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Provides consistent error responses for various exception types.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle BookNotFoundException.
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex) {
        log.error("Book not found: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle DuplicateBookException.
     */
    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateBookException(DuplicateBookException ex) {
        log.error("Duplicate book: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handle validation exceptions.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        log.error("Validation errors: {}", errors);
        
        ValidationErrorResponse response = new ValidationErrorResponse(
            "Validation failed",
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now(),
            errors
        );
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle generic exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Internal server error", ex);
        return buildErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Build a standardized error response.
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponse response = new ErrorResponse(
            message,
            status.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(response, status);
    }

    /**
     * Standard error response structure.
     */
    record ErrorResponse(String message, int status, LocalDateTime timestamp) {}

    /**
     * Validation error response with field errors.
     */
    record ValidationErrorResponse(String message, int status, LocalDateTime timestamp, Map<String, String> errors) {}
}