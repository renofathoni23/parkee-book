package com.example.parkee_book_library.controller;

import com.example.parkee_book_library.dto.BaseResponse;
import com.example.parkee_book_library.dto.request.BookRequest;
import com.example.parkee_book_library.model.Book;
import com.example.parkee_book_library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public BaseResponse createBook(@RequestBody BookRequest bookRequest){
        bookService.create(bookRequest);
        return BaseResponse.success("Booked created successfully", "ok");
    }
}
