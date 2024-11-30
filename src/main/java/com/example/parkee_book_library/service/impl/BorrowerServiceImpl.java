package com.example.parkee_book_library.service.impl;

import com.example.parkee_book_library.dto.CustomPageResponse;
import com.example.parkee_book_library.dto.request.BorrowRequest;
import com.example.parkee_book_library.exception.BusinessException;
import com.example.parkee_book_library.model.Borrower;
import com.example.parkee_book_library.repository.BorrowerRepository;
import com.example.parkee_book_library.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowerServiceImpl implements BorrowerService {
    @Autowired
    private BorrowerRepository borrowerRepository;
    @Override
    public void create(BorrowRequest request) {
        validateBorrower(request);
        Borrower borrower = new Borrower();
        borrower.setName(request.getName());
        borrower.setKtpNumber(request.getKtpNumber());
        borrower.setEmail(request.getEmail());
        borrowerRepository.save(borrower);
    }

    @Override
    public CustomPageResponse<Borrower> getData(Pageable pageable) {
        Page<Borrower> borrowerPage = borrowerRepository.findAll(pageable);
        CustomPageResponse<Borrower> customPageResponse = new CustomPageResponse<>();
        customPageResponse.setPage(pageable.getPageNumber() + 1);
        customPageResponse.setSize(pageable.getPageSize());
        customPageResponse.setTotalData(borrowerPage.getTotalElements());
        customPageResponse.setData(borrowerPage.getContent());
        return customPageResponse;
    }

    private void validateBorrower(BorrowRequest request){
        Optional<Borrower> optionalBorrower = borrowerRepository.findByKtpNumber(request.getKtpNumber());
        if(optionalBorrower.isPresent()){
            throw new BusinessException("Borrower is already exist");
        }
    }
}
