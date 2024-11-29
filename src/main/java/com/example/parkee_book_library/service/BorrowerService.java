package com.example.parkee_book_library.service;

import com.example.parkee_book_library.dto.request.BorrowRequest;

public interface BorrowerService {
    void create(BorrowRequest request);
}
