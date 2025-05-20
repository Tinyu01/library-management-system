package com.library.service;

import com.library.dto.BookDTO;
import com.library.dto.BookResponseDTO;
import com.library.model.Book;

import java.util.List;

/**
 * Service interface defining operations for book management.
 */
public interface BookService {
    
    /**
     * Get all books in the library.
     * 
     * @return List of all books
     */
    List<BookResponseDTO> getAllBooks();
    
    /**
     * Get a book by its ID.
     * 
     * @param id The book ID
     * @return The book if found
     * @throws com.library.exception.BookNotFoundException if the book is not found
     */
    BookResponseDTO getBookById(Long id);
    
    /**
     * Check the availability of a book by its title.
     * 
     * @param title The book title
     * @return The availability status message
     * @throws com.library.exception.BookNotFoundException if the book is not found
     */
    String checkBookAvailability(String title);
    
    /**
     * Add a new book to the library.
     * 
     * @param bookDTO The book data
     * @return The newly created book
     * @throws com.library.exception.DuplicateBookException if the book title already exists
     */
    BookResponseDTO addBook(BookDTO bookDTO);
    
    /**
     * Update a book's information.
     * 
     * @param id The book ID
     * @param bookDTO The updated book data
     * @return The updated book
     * @throws com.library.exception.BookNotFoundException if the book is not found
     * @throws com.library.exception.DuplicateBookException if the new title already exists
     */
    BookResponseDTO updateBook(Long id, BookDTO bookDTO);
    
    /**
     * Update only a book's title.
     * 
     * @param id The book ID
     * @param newTitle The new title
     * @return The updated book
     * @throws com.library.exception.BookNotFoundException if the book is not found
     * @throws com.library.exception.DuplicateBookException if the new title already exists
     */
    BookResponseDTO updateBookTitle(Long id, String newTitle);
    
    /**
     * Update a book's title by specifying the old title.
     * 
     * @param oldTitle The current book title
     * @param newTitle The new title
     * @return The updated book
     * @throws com.library.exception.BookNotFoundException if the book is not found
     * @throws com.library.exception.DuplicateBookException if the new title already exists
     */
    BookResponseDTO updateBookTitle(String oldTitle, String newTitle);
    
    /**
     * Delete a book from the library.
     * 
     * @param id The book ID
     * @throws com.library.exception.BookNotFoundException if the book is not found
     */
    void deleteBook(Long id);
    
    /**
     * Toggle a book's availability status.
     * 
     * @param id The book ID
     * @return The updated book
     * @throws com.library.exception.BookNotFoundException if the book is not found
     */
    BookResponseDTO toggleAvailability(Long id);
}