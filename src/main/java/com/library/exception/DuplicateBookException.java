package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to add a book with a title or ISBN
 * that already exists in the system.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateBookException extends RuntimeException {
    
    public DuplicateBookException(String message) {
        super(message);
    }
    
    public DuplicateBookException(String field, String value) {
        super("Book with " + field + " '" + value + "' already exists in the library.");
    }
}