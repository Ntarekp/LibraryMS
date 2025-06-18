package org.kaiLearn.ntare_LMS.dto;

import lombok.Data;
import org.kaiLearn.ntare_LMS.enums.BookAvailabilityStatus;

import java.util.List;

@Data
public class CreateMultipleBooksRequest {
    private List<BookData> books;
    
    @Data
    public static class BookData {
        private String title;
        private String author;
        private String isbn;
        private BookAvailabilityStatus availabilityStatus;
    }
} 