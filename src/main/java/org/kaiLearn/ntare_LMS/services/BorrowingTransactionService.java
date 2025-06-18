package org.kaiLearn.ntare_LMS.services;

import org.kaiLearn.ntare_LMS.dto.BorrowingTransactionResponse;
import org.kaiLearn.ntare_LMS.dto.CreateBorrowingRequest;
import org.kaiLearn.ntare_LMS.entities.Book;
import org.kaiLearn.ntare_LMS.entities.BorrowingTransaction;
import org.kaiLearn.ntare_LMS.enums.BookAvailabilityStatus;
import org.kaiLearn.ntare_LMS.enums.TransactionStatus;
import org.kaiLearn.ntare_LMS.repositories.BorrowingTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BorrowingTransactionService {
    
    @Autowired
    private BorrowingTransactionRepository borrowingTransactionRepository;
    
    @Autowired
    private BookService bookService;
    
    public BorrowingTransactionResponse createBorrowingTransaction(CreateBorrowingRequest request) {
        // Get the book by ISBN
        Book book = bookService.getBookEntityByIsbn(request.getIsbn());
        
        // Check if book is available for borrowing
        if (book.getAvailabilityStatus() != BookAvailabilityStatus.AVAILABLE) {
            throw new RuntimeException("Book with ISBN " + request.getIsbn() + " is not available for borrowing");
        }
        
        // Create borrowing transaction
        BorrowingTransaction transaction = new BorrowingTransaction();
        transaction.setBook(book);
        transaction.setBorrowerName(request.getBorrowerName());
        transaction.setBorrowDate(request.getBorrowDate() != null ? request.getBorrowDate() : LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING);
        
        BorrowingTransaction savedTransaction = borrowingTransactionRepository.save(transaction);
        
        // Update book availability status to BORROWED
        bookService.updateBookAvailability(book.getId(), BookAvailabilityStatus.BORROWED);
        
        return convertToBorrowingTransactionResponse(savedTransaction);
    }
    
    public BorrowingTransactionResponse returnBook(Long transactionId) {
        Optional<BorrowingTransaction> transactionOptional = borrowingTransactionRepository.findById(transactionId);
        if (transactionOptional.isPresent()) {
            BorrowingTransaction transaction = transactionOptional.get();
            
            // Check if transaction is already returned
            if (transaction.getStatus() == TransactionStatus.RETURNED) {
                throw new RuntimeException("Transaction is already returned");
            }
            
            // Update transaction status
            transaction.setStatus(TransactionStatus.RETURNED);
            transaction.setReturnDate(LocalDateTime.now());
            
            BorrowingTransaction savedTransaction = borrowingTransactionRepository.save(transaction);
            
            // Update book availability status to AVAILABLE
            bookService.updateBookAvailability(transaction.getBook().getId(), BookAvailabilityStatus.AVAILABLE);
            
            return convertToBorrowingTransactionResponse(savedTransaction);
        } else {
            throw new RuntimeException("Borrowing transaction with ID " + transactionId + " not found");
        }
    }
    
    public BookAvailabilityStatus getBookAvailabilityByTransactionId(Long transactionId) {
        Optional<BorrowingTransaction> transactionOptional = borrowingTransactionRepository.findById(transactionId);
        if (transactionOptional.isPresent()) {
            BorrowingTransaction transaction = transactionOptional.get();
            return transaction.getBook().getAvailabilityStatus();
        } else {
            throw new RuntimeException("Borrowing transaction with ID " + transactionId + " not found");
        }
    }
    
    private BorrowingTransactionResponse convertToBorrowingTransactionResponse(BorrowingTransaction transaction) {
        BorrowingTransactionResponse response = new BorrowingTransactionResponse();
        response.setId(transaction.getId());
        response.setBookId(transaction.getBook().getId());
        response.setBookTitle(transaction.getBook().getTitle());
        response.setBookIsbn(transaction.getBook().getIsbn());
        response.setBorrowerName(transaction.getBorrowerName());
        response.setBorrowDate(transaction.getBorrowDate());
        response.setReturnDate(transaction.getReturnDate());
        response.setStatus(transaction.getStatus());
        return response;
    }
} 