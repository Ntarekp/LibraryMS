package org.kaiLearn.ntare_LMS.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateMultipleBooksResponse {
    private List<BookResponse> createdBooks;
    private List<String> errors;
    private int totalRequested;
    private int successfullyCreated;
    private int failedToCreate;
} 