package org.kaiLearn.ntare_LMS.controllers;

import org.kaiLearn.ntare_LMS.dto.BorrowingTransactionResponse;
import org.kaiLearn.ntare_LMS.dto.CreateBorrowingRequest;
import org.kaiLearn.ntare_LMS.enums.BookAvailabilityStatus;
import org.kaiLearn.ntare_LMS.services.BorrowingTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrowing")
@CrossOrigin(origins = "*")
public class BorrowingController {
    
    @Autowired
    private BorrowingTransactionService borrowingTransactionService;
    
    @PostMapping
    public ResponseEntity<BorrowingTransactionResponse> createBorrowingTransaction(@RequestBody CreateBorrowingRequest request) {
        try {
            BorrowingTransactionResponse response = borrowingTransactionService.createBorrowingTransaction(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{transactionId}/availability")
    public ResponseEntity<BookAvailabilityStatus> getBookAvailabilityByTransactionId(@PathVariable Long transactionId) {
        try {
            BookAvailabilityStatus availability = borrowingTransactionService.getBookAvailabilityByTransactionId(transactionId);
            return ResponseEntity.ok(availability);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{transactionId}/return")
    public ResponseEntity<BorrowingTransactionResponse> returnBook(@PathVariable Long transactionId) {
        try {
            BorrowingTransactionResponse response = borrowingTransactionService.returnBook(transactionId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 