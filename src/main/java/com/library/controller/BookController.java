package com.library.controller;

import com.library.dto.BookDTO;
import com.library.dto.BookResponseDTO;
import com.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for managing books in the library.
 * Provides endpoints for CRUD operations and availability checking.
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Validated
@Tag(name = "Book Controller", description = "API endpoints for managing books in the library")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves all books in the library")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves a book by its ID")
    public ResponseEntity<BookResponseDTO> getBookById(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/{title}/availability")
    @Operation(summary = "Check book availability", description = "Checks if a book is available by its title")
    public ResponseEntity<Map<String, String>> checkBookAvailability(
            @Parameter(description = "Book title", required = true)
            @PathVariable String title) {
        String availabilityStatus = bookService.checkBookAvailability(title);
        return ResponseEntity.ok(Map.of("status", availabilityStatus));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new book", description = "Adds a new book to the library")
    public ResponseEntity<BookResponseDTO> addBook(
            @Parameter(description = "Book details", required = true)
            @Valid @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.addBook(bookDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book details", description = "Updates all details of a book by its ID")
    public ResponseEntity<BookResponseDTO> updateBook(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated book details", required = true)
            @Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @PatchMapping("/{id}/title")
    @Operation(summary = "Update book title", description = "Updates only the title of a book by its ID")
    public ResponseEntity<BookResponseDTO> updateBookTitle(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "New title", required = true)
            @RequestParam @NotBlank String newTitle) {
        return ResponseEntity.ok(bookService.updateBookTitle(id, newTitle));
    }

    @PatchMapping("/title")
    @Operation(summary = "Update book title by old title", description = "Updates a book's title by specifying its current title")
    public ResponseEntity<BookResponseDTO> updateBookTitleByOldTitle(
            @Parameter(description = "Current title", required = true)
            @RequestParam @NotBlank String oldTitle,
            @Parameter(description = "New title", required = true)
            @RequestParam @NotBlank String newTitle) {
        return ResponseEntity.ok(bookService.updateBookTitle(oldTitle, newTitle));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a book", description = "Removes a book from the library by its ID")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/availability")
    @Operation(summary = "Toggle book availability", description = "Changes a book's availability status (available/checked out)")
    public ResponseEntity<BookResponseDTO> toggleAvailability(
            @Parameter(description = "Book ID", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(bookService.toggleAvailability(id));
    }
}