package org.kaiLearn.ntare_LMS.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kaiLearn.ntare_LMS.enums.BookAvailabilityStatus;

import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column(unique = true, nullable = false)
    private String isbn;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookAvailabilityStatus availabilityStatus = BookAvailabilityStatus.AVAILABLE;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BorrowingTransaction> borrowingTransactions;
} 