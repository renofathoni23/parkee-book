package com.example.parkee_book_library.service;

import com.example.parkee_book_library.dto.CustomPageResponse;
import com.example.parkee_book_library.dto.request.BorrowBookRequest;
import com.example.parkee_book_library.dto.response.BorrowHistoryResponse;
import com.example.parkee_book_library.model.BorrowHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BorrowHistoryService {
    void borrowBook(BorrowBookRequest request);
    void updateBorrowHistory(String id);
    CustomPageResponse<BorrowHistoryResponse> getBorrowHistory(Pageable pageable);
}
