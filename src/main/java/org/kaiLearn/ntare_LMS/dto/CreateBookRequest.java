package org.kaiLearn.ntare_LMS.dto;

import lombok.Data;
import org.kaiLearn.ntare_LMS.enums.BookAvailabilityStatus;

@Data
public class CreateBookRequest {
    private String title;
    private String author;
    private String isbn;
    private BookAvailabilityStatus availabilityStatus;
} 