package org.kaiLearn.ntare_LMS.repositories;

import org.kaiLearn.ntare_LMS.entities.Book;
import org.kaiLearn.ntare_LMS.enums.BookAvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByIsbn(String isbn);
    
    boolean existsByIsbn(String isbn);
    
    List<Book> findByAvailabilityStatus(BookAvailabilityStatus availabilityStatus);
    
    List<Book> findAllByOrderByTitleAsc();
} 