package com.example.parkee_book_library.service.impl;

import com.example.parkee_book_library.dto.CustomPageResponse;
import com.example.parkee_book_library.dto.request.BorrowBookRequest;
import com.example.parkee_book_library.dto.response.BookResponse;
import com.example.parkee_book_library.dto.response.BorrowHistoryResponse;
import com.example.parkee_book_library.dto.response.BorrowerResponse;
import com.example.parkee_book_library.enums.BookStatus;
import com.example.parkee_book_library.exception.BusinessException;
import com.example.parkee_book_library.model.Book;
import com.example.parkee_book_library.model.BorrowHistory;
import com.example.parkee_book_library.model.Borrower;
import com.example.parkee_book_library.repository.BookRepository;
import com.example.parkee_book_library.repository.BorrowHistoryRepository;
import com.example.parkee_book_library.repository.BorrowerRepository;
import com.example.parkee_book_library.service.BorrowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowHistoryServiceImpl implements BorrowHistoryService {
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;
    @Override
    @Transactional
    public void borrowBook(BorrowBookRequest request) {
        Borrower borrower = findBorrower(request.getBorrowerId());
        Book book = findBook(request.getBookId());
        validateBorrowBook(request, borrower, book);
        saveBorrowBook(request, borrower, book);
        decreaseStockBook(book);
    }

    @Override
    @Transactional
    public void updateBorrowHistory(String id) {
        BorrowHistory borrowHistory = findBorrowHistory(id);
        Book book = findBook(borrowHistory.getBook().getId());
        updateStatusBorrowHistory(borrowHistory);
        increaseStockBook(book);
    }

    @Override
    public CustomPageResponse<BorrowHistoryResponse> getBorrowHistory(Pageable pageable) {
        Page<BorrowHistory> borrowHistories = borrowHistoryRepository.findAll(pageable);
        CustomPageResponse<BorrowHistoryResponse> customPageResponse = new CustomPageResponse<>();
        customPageResponse.setPage(pageable.getPageNumber() + 1);
        customPageResponse.setSize(pageable.getPageSize());
        customPageResponse.setTotalData(borrowHistories.getTotalElements());
        customPageResponse.setData(convertBorrowHistory(borrowHistories.getContent()));
        return customPageResponse;
    }

    private List<BorrowHistoryResponse> convertBorrowHistory(List<BorrowHistory> borrowHistoryList){
        List<BorrowHistoryResponse> borrowHistoryResponses = new ArrayList<>();
        for(BorrowHistory borrowHistory : borrowHistoryList){
            BorrowHistoryResponse borrowHistoryResponse = new BorrowHistoryResponse();
            borrowHistoryResponse.setId(borrowHistory.getId());
            borrowHistoryResponse.setStatus(borrowHistory.getStatus());
            borrowHistoryResponse.setCreatedAt(borrowHistory.getCreatedAt());
            borrowHistoryResponse.setBorrowDate(borrowHistory.getBorrowDate());
            borrowHistoryResponse.setReturnDate(borrowHistory.getReturnDate());

            BookResponse bookResponse = convertBookResponse(borrowHistory.getBook());
            BorrowerResponse borrowerResponse = convertBorrowerResponse(borrowHistory.getBorrower());

            borrowHistoryResponse.setBook(bookResponse);
            borrowHistoryResponse.setBorrower(borrowerResponse);

            borrowHistoryResponses.add(borrowHistoryResponse);
        }
        return borrowHistoryResponses;
    }

    private BookResponse convertBookResponse(Book book){
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setIsbn(book.getIsbn());

        return bookResponse;
    }

    private BorrowerResponse convertBorrowerResponse(Borrower borrower){
        BorrowerResponse borrowerResponse = new BorrowerResponse();
        borrowerResponse.setId(borrower.getId());
        borrowerResponse.setName(borrower.getName());
        borrowerResponse.setEmail(borrower.getEmail());

        return borrowerResponse;
    }


    private void updateStatusBorrowHistory(BorrowHistory borrowHistory){
        if(!borrowHistory.getStatus().equals(BookStatus.BORROWED.name())){
            throw new BusinessException("Book lending has been completed");
        }

        LocalDate dateNow = LocalDate.now();
        if(dateNow.isBefore(borrowHistory.getReturnDate()) || dateNow.isEqual(borrowHistory.getReturnDate())){
            borrowHistory.setStatus(BookStatus.ON_TIME.name());
        }
        else{
            borrowHistory.setStatus(BookStatus.LATE.name());
        }
    }
    private void decreaseStockBook(Book book){
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);
    }

    private void increaseStockBook(Book book){
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);
    }

    private void saveBorrowBook(BorrowBookRequest request, Borrower borrower, Book book){
        BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setBorrower(borrower);
        borrowHistory.setBook(book);
        borrowHistory.setStatus(BookStatus.BORROWED.name());
        borrowHistory.setBorrowDate(LocalDate.now());
        borrowHistory.setReturnDate(request.getReturnDate());
        borrowHistoryRepository.save(borrowHistory);
    }

    private void validateBorrowBook(BorrowBookRequest request, Borrower borrower, Book book){
        if(book.getStock() < 1){
            throw new BusinessException("Book is out of stock");
        }

        boolean isBorrowOnGoingExist = borrowHistoryRepository.existByBorrowerIdAndStatus(borrower.getId(), BookStatus.BORROWED.name());
        if(isBorrowOnGoingExist){
            throw new BusinessException("Borrower have still borrow other book");
        }

        LocalDate currentDate = LocalDate.now();
        Long daysBetween = ChronoUnit.DAYS.between(currentDate, request.getReturnDate());
        if(daysBetween > 30){
            throw new BusinessException("The return date must be less than 30 days");
        }
    }

    private Borrower findBorrower(Long borrowerId){
        Optional<Borrower> optionalBorrower = borrowerRepository.findById(borrowerId);
        if(optionalBorrower.isEmpty()){
            throw new BusinessException("Borrower is not exist");
        }
        return optionalBorrower.get();
    }

    private Book findBook(String bookId){
        Optional<Book> optionalBook = bookRepository.findByIdPessimisticLock(bookId);
        if(optionalBook.isEmpty()){
            throw new BusinessException("Book is not exist");
        }
        return optionalBook.get();
    }

    private BorrowHistory findBorrowHistory(String id){
        Optional<BorrowHistory> borrowHistoryOptional = borrowHistoryRepository.findById(id);
        if(borrowHistoryOptional.isEmpty()){
            throw new BusinessException("Record data not found");
        }
        return borrowHistoryOptional.get();
    }

}
