package com.example.parkee_book_library.service;

import com.example.parkee_book_library.dto.CustomPageResponse;
import com.example.parkee_book_library.dto.request.BookRequest;
import com.example.parkee_book_library.model.Book;
import org.springframework.data.domain.Pageable;

public interface BookService {
    void create (BookRequest request);
    CustomPageResponse<Book> getData(Pageable pageable);
}
