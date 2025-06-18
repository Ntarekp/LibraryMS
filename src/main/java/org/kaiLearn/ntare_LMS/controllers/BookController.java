package org.kaiLearn.ntare_LMS.controllers;

import org.kaiLearn.ntare_LMS.dto.BookResponse;
import org.kaiLearn.ntare_LMS.dto.CreateBookRequest;
import org.kaiLearn.ntare_LMS.dto.CreateMultipleBooksRequest;
import org.kaiLearn.ntare_LMS.dto.CreateMultipleBooksResponse;
import org.kaiLearn.ntare_LMS.enums.BookAvailabilityStatus;
import org.kaiLearn.ntare_LMS.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody CreateBookRequest request) {
        try {
            BookResponse response = bookService.createBook(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/bulk")
    public ResponseEntity<CreateMultipleBooksResponse> createMultipleBooks(@RequestBody CreateMultipleBooksRequest request) {
        try {
            CreateMultipleBooksResponse response = bookService.createMultipleBooks(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/isbn/{isbn}")
    public ResponseEntity<Void> deleteBookByIsbn(@PathVariable String isbn) {
        try {
            bookService.deleteBookByIsbn(isbn);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        try {
            List<BookResponse> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<BookResponse>> getAvailableBooks() {
        try {
            List<BookResponse> availableBooks = bookService.getAvailableBooks();
            return ResponseEntity.ok(availableBooks);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/borrowed")
    public ResponseEntity<List<BookResponse>> getBorrowedBooks() {
        try {
            List<BookResponse> borrowedBooks = bookService.getBorrowedBooks();
            return ResponseEntity.ok(borrowedBooks);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        try {
            BookResponse response = bookService.getBookByIsbn(isbn);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{isbn}/availability")
    public ResponseEntity<BookAvailabilityStatus> getBookAvailability(@PathVariable String isbn) {
        try {
            BookAvailabilityStatus availability = bookService.getBookAvailability(isbn);
            return ResponseEntity.ok(availability);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 