package org.kaiLearn.ntare_LMS.repositories;

import org.kaiLearn.ntare_LMS.entities.BorrowingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    
    List<BorrowingTransaction> findByBookId(Long bookId);
    
    List<BorrowingTransaction> findByBorrowerName(String borrowerName);
} 