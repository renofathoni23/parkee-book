package com.example.parkee_book_library.controller;

import com.example.parkee_book_library.dto.BaseResponse;
import com.example.parkee_book_library.dto.CustomPageResponse;
import com.example.parkee_book_library.dto.request.BookRequest;
import com.example.parkee_book_library.model.Book;
import com.example.parkee_book_library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public BaseResponse getBook(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
                                @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ){
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(direction, sortBy));
        CustomPageResponse<Book> data = bookService.getData(pageable);
        return BaseResponse.success("fetch data successfully", data);
    }
}
