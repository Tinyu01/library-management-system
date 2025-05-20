package com.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for book creation and updates.
 * Contains validation rules for input data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    
    @NotBlank(message = "Book title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;
    
    @Size(max = 255, message = "Author name must be less than 255 characters")
    private String author;
    
    @Size(min = 10, max = 20, message = "ISBN must be between 10 and 20 characters")
    private String isbn;
    
    private boolean available = true;
}