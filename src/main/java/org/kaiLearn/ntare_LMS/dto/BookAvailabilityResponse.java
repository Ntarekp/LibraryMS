package org.kaiLearn.ntare_LMS.dto;

import lombok.Data;
import org.kaiLearn.ntare_LMS.enums.BookAvailabilityStatus;

@Data
public class BookAvailabilityResponse {
    private String isbn;
    private String title;
    private BookAvailabilityStatus availabilityStatus;
} 