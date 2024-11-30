package com.example.parkee_book_library.service;

import com.example.parkee_book_library.dto.CustomPageResponse;
import com.example.parkee_book_library.dto.request.BorrowRequest;
import com.example.parkee_book_library.model.Borrower;
import org.springframework.data.domain.Pageable;

public interface BorrowerService {
    void create(BorrowRequest request);
    CustomPageResponse<Borrower> getData(Pageable pageable);
}
