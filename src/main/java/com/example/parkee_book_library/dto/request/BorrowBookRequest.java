package com.example.parkee_book_library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBookRequest {
    private Long borrowerId;
    private String bookId;
    private LocalDate returnDate;
}
