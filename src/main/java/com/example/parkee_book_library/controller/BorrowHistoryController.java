package com.example.parkee_book_library.controller;

import com.example.parkee_book_library.dto.BaseResponse;
import com.example.parkee_book_library.dto.request.BorrowBookRequest;
import com.example.parkee_book_library.service.BorrowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public BaseResponse updateStatus(@RequestParam("id") String id){
        borrowHistoryService.updateBorrowHistory(id);
        return BaseResponse.success("Update status borrowed book successfully", "ok");
    }
}
