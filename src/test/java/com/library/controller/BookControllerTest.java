package com.library.service;

import com.library.dto.BookDTO;
import com.library.dto.BookResponseDTO;
import com.library.exception.BookNotFoundException;
import com.library.exception.DuplicateBookException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book sampleBook;
    private BookDTO sampleBookDTO;
    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        // Initialize sample book
        sampleBook = Book.builder()
                .id(1L)
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .isbn("9780743273565")
                .available(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // Initialize sample book DTO
        sampleBookDTO = BookDTO.builder()
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .isbn("9780743273565")
                .available(true)
                .build();
    }

    @Test
    @DisplayName("Should return all books when getAllBooks is called")
    void getAllBooks_ShouldReturnAllBooks() {
        // Given
        when(bookRepository.findAll()).thenReturn(List.of(sampleBook));

        // When
        List<BookResponseDTO> result = bookService.getAllBooks();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The Great Gatsby", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return book by ID when getBookById is called with valid ID")
    void getBookById_WithValidId_ShouldReturnBook() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));

        // When
        BookResponseDTO result = bookService.getBookById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("The Great Gatsby", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw BookNotFoundException when getBookById is called with invalid ID")
    void getBookById_WithInvalidId_ShouldThrowException() {
        // Given
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(999L));
        verify(bookRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should return available status when book is available")
    void checkBookAvailability_ForAvailableBook_ShouldReturnAvailableStatus() {
        // Given
        when(bookRepository.findByTitle("The Great Gatsby")).thenReturn(Optional.of(sampleBook));

        // When
        String result = bookService.checkBookAvailability("The Great Gatsby");

        // Then
        assertEquals("The book 'The Great Gatsby' is available.", result);
        verify(bookRepository, times(1)).findByTitle("The Great Gatsby");
    }

    @Test
    @DisplayName("Should return checked out status when book is not available")
    void checkBookAvailability_ForCheckedOutBook_ShouldReturnCheckedOutStatus() {
        // Given
        Book checkedOutBook = Book.builder()
                .id(2L)
                .title("1984")
                .author("George Orwell")
                .available(false)
                .build();
        when(bookRepository.findByTitle("1984")).thenReturn(Optional.of(checkedOutBook));

        // When
        String result = bookService.checkBookAvailability("1984");

        // Then
        assertEquals("The book '1984' is checked out.", result);
        verify(bookRepository, times(1)).findByTitle("1984");
    }

    @Test
    @DisplayName("Should return not in collection status when book does not exist")
    void checkBookAvailability_ForNonExistentBook_ShouldReturnNotInCollectionStatus() {
        // Given
        when(bookRepository.findByTitle("Unknown Book")).thenReturn(Optional.empty());

        // When
        String result = bookService.checkBookAvailability("Unknown Book");

        // Then
        assertEquals("The book 'Unknown Book' is not in the library's collection.", result);
        verify(bookRepository, times(1)).findByTitle("Unknown Book");
    }

    @Test
    @DisplayName("Should add book successfully when valid data is provided")
    void addBook_WithValidData_ShouldAddSuccessfully() {
        // Given
        when(bookRepository.existsByTitle(anyString())).thenReturn(false);
        when(bookRepository.existsByIsbn(anyString())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(sampleBook);

        // When
        BookResponseDTO result = bookService.addBook(sampleBookDTO);

        // Then
        assertNotNull(result);
        assertEquals("The Great Gatsby", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw DuplicateBookException when adding book with existing title")
    void addBook_WithExistingTitle_ShouldThrowException() {
        // Given
        when(bookRepository.existsByTitle("The Great Gatsby")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateBookException.class, () -> bookService.addBook(sampleBookDTO));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Should update book title successfully when valid data is provided")
    void updateBookTitle_WithValidData_ShouldUpdateSuccessfully() {
        // Given
        when(bookRepository.findByTitle("The Great Gatsby")).thenReturn(Optional.of(sampleBook));
        when(bookRepository.existsByTitle("The Greatest Gatsby")).thenReturn(false);
        
        Book updatedBook = Book.builder()
                .id(1L)
                .title("The Greatest Gatsby")
                .author("F. Scott Fitzgerald")
                .isbn("9780743273565")
                .available(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // When
        BookResponseDTO result = bookService.updateBookTitle("The Great Gatsby", "The Greatest Gatsby");

        // Then
        assertNotNull(result);
        assertEquals("The Greatest Gatsby", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw BookNotFoundException when updating non-existent book title")
    void updateBookTitle_WithNonExistentOldTitle_ShouldThrowException() {
        // Given
        when(bookRepository.findByTitle("Non-existent Book")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BookNotFoundException.class, 
                () -> bookService.updateBookTitle("Non-existent Book", "New Title"));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw DuplicateBookException when updating to existing title")
    void updateBookTitle_WithExistingNewTitle_ShouldThrowException() {
        // Given
        when(bookRepository.findByTitle("The Great Gatsby")).thenReturn(Optional.of(sampleBook));
        when(bookRepository.existsByTitle("1984")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateBookException.class, 
                () -> bookService.updateBookTitle("The Great Gatsby", "1984"));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Should delete book successfully when valid ID is provided")
    void deleteBook_WithValidId_ShouldDeleteSuccessfully() {
        // Given
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        // When
        assertDoesNotThrow(() -> bookService.deleteBook(1L));

        // Then
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw BookNotFoundException when deleting non-existent book")
    void deleteBook_WithInvalidId_ShouldThrowException() {
        // Given
        when(bookRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(999L));
        verify(bookRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should toggle availability successfully")
    void toggleAvailability_ShouldToggleSuccessfully() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook));
        
        Book toggledBook = Book.builder()
                .id(1L)
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .isbn("9780743273565")
                .available(false) // toggled from true to false
                .createdAt(now)
                .updatedAt(now)
                .build();
        
        when(bookRepository.save(any(Book.class))).thenReturn(toggledBook);

        // When
        BookResponseDTO result = bookService.toggleAvailability(1L);

        // Then
        assertNotNull(result);
        assertFalse(result.isAvailable());
        verify(bookRepository, times(1)).save(any(Book.class));
    }
}