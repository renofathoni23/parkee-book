package com.example.parkee_book_library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowHistoryResponse {
    private String id;
    private LocalDateTime createdAt;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private BorrowerResponse borrower;
    private BookResponse book;
    private String status;

}
