package com.example.parkee_book_library.service;

import com.example.parkee_book_library.dto.request.BorrowBookRequest;
import com.example.parkee_book_library.dto.request.BorrowRequest;

public interface BorrowHistoryService {
    void borrowBook(BorrowBookRequest request);
}
