package com.library.service;

import com.library.dto.BookDTO;
import com.library.dto.BookResponseDTO;
import com.library.exception.BookNotFoundException;
import com.library.exception.DuplicateBookException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of BookService that provides book management functionality.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<BookResponseDTO> getAllBooks() {
        log.info("Retrieving all books");
        return bookRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        log.info("Finding book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return mapToResponseDTO(book);
    }

    @Override
    public String checkBookAvailability(String title) {
        log.info("Checking availability for book: {}", title);
        return bookRepository.findByTitle(title)
                .map(book -> book.isAvailable() ?
                        "The book '" + title + "' is available." :
                        "The book '" + title + "' is checked out.")
                .orElse("The book '" + title + "' is not in the library's collection.");
    }

    @Override
    @Transactional
    public BookResponseDTO addBook(BookDTO bookDTO) {
        log.info("Adding new book: {}", bookDTO.getTitle());
        
        // Check if book with same title already exists
        if (bookRepository.existsByTitle(bookDTO.getTitle())) {
            throw new DuplicateBookException("title", bookDTO.getTitle());
        }
        
        // Check if ISBN is provided and already exists
        if (bookDTO.getIsbn() != null && !bookDTO.getIsbn().isBlank() && 
            bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateBookException("ISBN", bookDTO.getIsbn());
        }
        
        Book book = mapToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        log.info("Book added successfully with id: {}", savedBook.getId());
        
        return mapToResponseDTO(savedBook);
    }

    @Override
    @Transactional
    public BookResponseDTO updateBook(Long id, BookDTO bookDTO) {
        log.info("Updating book with id: {}", id);
        
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        // Check if new title already exists for a different book
        if (!existingBook.getTitle().equals(bookDTO.getTitle()) && 
            bookRepository.existsByTitle(bookDTO.getTitle())) {
            throw new DuplicateBookException("title", bookDTO.getTitle());
        }
        
        // Check if new ISBN already exists for a different book
        if (bookDTO.getIsbn() != null && !bookDTO.getIsbn().isBlank() &&
            !bookDTO.getIsbn().equals(existingBook.getIsbn()) &&
            bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateBookException("ISBN", bookDTO.getIsbn());
        }
        
        // Update fields
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setAvailable(bookDTO.isAvailable());
        
        Book updatedBook = bookRepository.save(existingBook);
        log.info("Book updated successfully: {}", updatedBook.getTitle());
        
        return mapToResponseDTO(updatedBook);
    }

    @Override
    @Transactional
    public BookResponseDTO updateBookTitle(Long id, String newTitle) {
        log.info("Updating title of book with id: {} to '{}'", id, newTitle);
        
        if (newTitle == null || newTitle.isBlank()) {
            throw new IllegalArgumentException("New title cannot be empty");
        }
        
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        // Check if new title already exists
        if (bookRepository.existsByTitle(newTitle)) {
            throw new DuplicateBookException("title", newTitle);
        }
        
        existingBook.setTitle(newTitle);
        Book updatedBook = bookRepository.save(existingBook);
        log.info("Book title updated successfully to: {}", newTitle);
        
        return mapToResponseDTO(updatedBook);
    }

    @Override
    @Transactional
    public BookResponseDTO updateBookTitle(String oldTitle, String newTitle) {
        log.info("Updating book title from '{}' to '{}'", oldTitle, newTitle);
        
        // Validate inputs
        if (oldTitle == null || oldTitle.isBlank() || newTitle == null || newTitle.isBlank()) {
            throw new IllegalArgumentException("Book titles cannot be empty");
        }
        
        // Find book by old title
        Book existingBook = bookRepository.findByTitle(oldTitle)
                .orElseThrow(() -> new BookNotFoundException("title", oldTitle));
        
        // Check if new title already exists
        if (bookRepository.existsByTitle(newTitle)) {
            throw new DuplicateBookException("title", newTitle);
        }
        
        // Update title
        existingBook.setTitle(newTitle);
        Book updatedBook = bookRepository.save(existingBook);
        log.info("Book title updated successfully from '{}' to '{}'", oldTitle, newTitle);
        
        return mapToResponseDTO(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        log.info("Deleting book with id: {}", id);
        
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        
        bookRepository.deleteById(id);
        log.info("Book deleted successfully with id: {}", id);
    }

    @Override
    @Transactional
    public BookResponseDTO toggleAvailability(Long id) {
        log.info("Toggling availability of book with id: {}", id);
        
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        existingBook.setAvailable(!existingBook.isAvailable());
        Book updatedBook = bookRepository.save(existingBook);
        
        String status = updatedBook.isAvailable() ? "available" : "checked out";
        log.info("Book '{}' is now {}", updatedBook.getTitle(), status);
        
        return mapToResponseDTO(updatedBook);
    }

    /**
     * Maps a Book entity to a BookResponseDTO.
     */
    private BookResponseDTO mapToResponseDTO(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .available(book.isAvailable())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

    /**
     * Maps a BookDTO to a Book entity.
     */
    private Book mapToEntity(BookDTO bookDTO) {
        return Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .available(bookDTO.isAvailable())
                .build();
    }
}