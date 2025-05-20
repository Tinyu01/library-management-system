package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for book data.
 * Used to return book information to clients.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Returns a string representation of the book's availability status.
     * 
     * @return A descriptive string about the book's availability
     */
    public String getAvailabilityStatus() {
        return available ? 
               "The book '" + title + "' is available." : 
               "The book '" + title + "' is checked out.";
    }
}