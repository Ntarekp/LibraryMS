package org.kaiLearn.ntare_LMS.services;

import org.kaiLearn.ntare_LMS.dto.BookResponse;
import org.kaiLearn.ntare_LMS.dto.CreateBookRequest;
import org.kaiLearn.ntare_LMS.dto.CreateMultipleBooksRequest;
import org.kaiLearn.ntare_LMS.dto.CreateMultipleBooksResponse;
import org.kaiLearn.ntare_LMS.entities.Book;
import org.kaiLearn.ntare_LMS.enums.BookAvailabilityStatus;
import org.kaiLearn.ntare_LMS.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public BookResponse createBook(CreateBookRequest request) {
        // Check if book with ISBN already exists
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new RuntimeException("Book with ISBN " + request.getIsbn() + " already exists");
        }
        
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setAvailabilityStatus(request.getAvailabilityStatus() != null ? 
            request.getAvailabilityStatus() : BookAvailabilityStatus.AVAILABLE);
        
        Book savedBook = bookRepository.save(book);
        return convertToBookResponse(savedBook);
    }
    
    public CreateMultipleBooksResponse createMultipleBooks(CreateMultipleBooksRequest request) {
        CreateMultipleBooksResponse response = new CreateMultipleBooksResponse();
        List<BookResponse> createdBooks = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        
        int totalRequested = request.getBooks().size();
        int successfullyCreated = 0;
        int failedToCreate = 0;
        
        for (CreateMultipleBooksRequest.BookData bookData : request.getBooks()) {
            try {
                // Check if book with ISBN already exists
                if (bookRepository.existsByIsbn(bookData.getIsbn())) {
                    errors.add("Book with ISBN " + bookData.getIsbn() + " already exists");
                    failedToCreate++;
                    continue;
                }
                
                Book book = new Book();
                book.setTitle(bookData.getTitle());
                book.setAuthor(bookData.getAuthor());
                book.setIsbn(bookData.getIsbn());
                book.setAvailabilityStatus(bookData.getAvailabilityStatus() != null ? 
                    bookData.getAvailabilityStatus() : BookAvailabilityStatus.AVAILABLE);
                
                Book savedBook = bookRepository.save(book);
                createdBooks.add(convertToBookResponse(savedBook));
                successfullyCreated++;
                
            } catch (Exception e) {
                errors.add("Failed to create book with ISBN " + bookData.getIsbn() + ": " + e.getMessage());
                failedToCreate++;
            }
        }
        
        response.setCreatedBooks(createdBooks);
        response.setErrors(errors);
        response.setTotalRequested(totalRequested);
        response.setSuccessfullyCreated(successfullyCreated);
        response.setFailedToCreate(failedToCreate);
        
        return response;
    }
    
    public void deleteBook(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            
            // Check if book is currently borrowed
            if (book.getAvailabilityStatus() == BookAvailabilityStatus.BORROWED) {
                throw new RuntimeException("Cannot delete book with ID " + bookId + " as it is currently borrowed");
            }
            
            bookRepository.deleteById(bookId);
        } else {
            throw new RuntimeException("Book with ID " + bookId + " not found");
        }
    }
    
    public void deleteBookByIsbn(String isbn) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            
            // Check if book is currently borrowed
            if (book.getAvailabilityStatus() == BookAvailabilityStatus.BORROWED) {
                throw new RuntimeException("Cannot delete book with ISBN " + isbn + " as it is currently borrowed");
            }
            
            bookRepository.deleteById(book.getId());
        } else {
            throw new RuntimeException("Book with ISBN " + isbn + " not found");
        }
    }
    
    public BookResponse getBookByIsbn(String isbn) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
        if (bookOptional.isPresent()) {
            return convertToBookResponse(bookOptional.get());
        } else {
            throw new RuntimeException("Book with ISBN " + isbn + " not found");
        }
    }
    
    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAllByOrderByTitleAsc();
        return books.stream()
                .map(this::convertToBookResponse)
                .collect(Collectors.toList());
    }
    
    public List<BookResponse> getAvailableBooks() {
        List<Book> availableBooks = bookRepository.findByAvailabilityStatus(BookAvailabilityStatus.AVAILABLE);
        return availableBooks.stream()
                .map(this::convertToBookResponse)
                .collect(Collectors.toList());
    }
    
    public List<BookResponse> getBorrowedBooks() {
        List<Book> borrowedBooks = bookRepository.findByAvailabilityStatus(BookAvailabilityStatus.BORROWED);
        return borrowedBooks.stream()
                .map(this::convertToBookResponse)
                .collect(Collectors.toList());
    }
    
    public BookAvailabilityStatus getBookAvailability(String isbn) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
        if (bookOptional.isPresent()) {
            return bookOptional.get().getAvailabilityStatus();
        } else {
            throw new RuntimeException("Book with ISBN " + isbn + " not found");
        }
    }
    
    public Book getBookEntityByIsbn(String isbn) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        } else {
            throw new RuntimeException("Book with ISBN " + isbn + " not found");
        }
    }
    
    public void updateBookAvailability(Long bookId, BookAvailabilityStatus status) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setAvailabilityStatus(status);
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Book with ID " + bookId + " not found");
        }
    }
    
    private BookResponse convertToBookResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setAvailabilityStatus(book.getAvailabilityStatus());
        return response;
    }
} 