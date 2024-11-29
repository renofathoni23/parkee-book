package com.example.parkee_book_library.controller;

import com.example.parkee_book_library.dto.BaseResponse;
import com.example.parkee_book_library.dto.request.BorrowRequest;
import com.example.parkee_book_library.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    @Autowired
    private BorrowerService borrowerService;

    @PostMapping
    public BaseResponse createBorrower(@RequestBody BorrowRequest request){
        borrowerService.create(request);
        return BaseResponse.success("Borrower data created successfully", "ok");
    }
}
