package org.kaiLearn.ntare_LMS.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBorrowingRequest {
    private String isbn;
    private String borrowerName;
    private LocalDateTime borrowDate;
} 