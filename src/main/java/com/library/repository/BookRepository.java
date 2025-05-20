package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Book entity.
 * Provides methods to interact with the database.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    /**
     * Find a book by its title.
     * 
     * @param title The title of the book to find
     * @return Optional containing the book if found, empty otherwise
     */
    Optional<Book> findByTitle(String title);
    
    /**
     * Check if a book with the given title exists.
     * 
     * @param title The title to check
     * @return true if the book exists, false otherwise
     */
    boolean existsByTitle(String title);
    
    /**
     * Find a book by its ISBN.
     * 
     * @param isbn The ISBN to search for
     * @return Optional containing the book if found, empty otherwise
     */
    Optional<Book> findByIsbn(String isbn);
    
    /**
     * Check if a book with the given ISBN exists.
     * 
     * @param isbn The ISBN to check
     * @return true if the book exists, false otherwise
     */
    boolean existsByIsbn(String isbn);
}