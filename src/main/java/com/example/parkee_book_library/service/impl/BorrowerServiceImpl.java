package com.example.parkee_book_library.service.impl;

import com.example.parkee_book_library.dto.request.BorrowRequest;
import com.example.parkee_book_library.exception.BusinessException;
import com.example.parkee_book_library.model.Borrower;
import com.example.parkee_book_library.repository.BorrowerRepository;
import com.example.parkee_book_library.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private void validateBorrower(BorrowRequest request){
        Optional<Borrower> optionalBorrower = borrowerRepository.findByKtpNumber(request.getKtpNumber());
        if(optionalBorrower.isPresent()){
            throw new BusinessException("Borrower is already exist");
        }
    }
}
