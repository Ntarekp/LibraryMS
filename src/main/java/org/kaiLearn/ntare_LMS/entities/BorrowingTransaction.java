package org.kaiLearn.ntare_LMS.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kaiLearn.ntare_LMS.enums.TransactionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "borrowing_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @Column(nullable = false)
    private String borrowerName;
    
    @Column(nullable = false)
    private LocalDateTime borrowDate;
    
    @Column
    private LocalDateTime returnDate;
    
    @Enumerated(EnumType.)
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;
} 