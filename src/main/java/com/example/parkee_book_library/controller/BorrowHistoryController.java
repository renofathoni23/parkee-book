package com.example.parkee_book_library.controller;

import com.example.parkee_book_library.dto.BaseResponse;
import com.example.parkee_book_library.dto.request.BorrowBookRequest;
import com.example.parkee_book_library.service.BorrowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrow-book")
public class BorrowHistoryController {

    @Autowired
    private BorrowHistoryService borrowHistoryService;


    @PostMapping
    public BaseResponse borrowBook(@RequestBody BorrowBookRequest request){
        borrowHistoryService.borrowBook(request);
        return BaseResponse.success("Borrow book data created successfully", "ok");
    }
}
