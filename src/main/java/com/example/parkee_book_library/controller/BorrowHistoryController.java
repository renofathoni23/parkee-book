package com.example.parkee_book_library.controller;

import com.example.parkee_book_library.dto.BaseResponse;
import com.example.parkee_book_library.dto.CustomPageResponse;
import com.example.parkee_book_library.dto.request.BorrowBookRequest;
import com.example.parkee_book_library.dto.response.BorrowHistoryResponse;
import com.example.parkee_book_library.service.BorrowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @GetMapping
    public BaseResponse getData(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                @RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection,
                                @RequestParam(value = "status", defaultValue = "") String status
                                ){
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(direction, sortBy));
        CustomPageResponse<BorrowHistoryResponse> data = borrowHistoryService.getBorrowHistory(status,pageable);
        return BaseResponse.success("fetch data successfully", data);
    }
}
