package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested book is not found in the system.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {
    
    public BookNotFoundException(String message) {
        super(message);
    }
    
    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }
    
    public BookNotFoundException(String field, String value) {
        super("Book not found with " + field + ": " + value);
    }
}