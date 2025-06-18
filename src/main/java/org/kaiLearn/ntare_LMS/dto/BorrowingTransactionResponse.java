package org.kaiLearn.ntare_LMS.dto;

import lombok.Data;
import org.kaiLearn.ntare_LMS.enums.TransactionStatus;

import java.time.LocalDateTime;

@Data
public class BorrowingTransactionResponse {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private String bookIsbn;
    private String borrowerName;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private TransactionStatus status;
} 