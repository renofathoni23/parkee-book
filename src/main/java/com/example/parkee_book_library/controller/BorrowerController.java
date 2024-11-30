package com.example.parkee_book_library.controller;

import com.example.parkee_book_library.dto.BaseResponse;
import com.example.parkee_book_library.dto.CustomPageResponse;
import com.example.parkee_book_library.dto.request.BorrowRequest;
import com.example.parkee_book_library.model.Borrower;
import com.example.parkee_book_library.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public BaseResponse getBorrower(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ){
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(direction, sortBy));
        CustomPageResponse<Borrower> data = borrowerService.getData(pageable);
        return BaseResponse.success("fetch data successfully", data);
    }
}
