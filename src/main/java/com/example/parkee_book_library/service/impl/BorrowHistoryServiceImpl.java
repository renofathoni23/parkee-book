package com.example.parkee_book_library.service.impl;

import com.example.parkee_book_library.dto.request.BorrowBookRequest;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        updateStockBook(book);
    }


    void updateStockBook(Book book){
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);
    }

    void saveBorrowBook(BorrowBookRequest request, Borrower borrower, Book book){
        BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setBorrower(borrower);
        borrowHistory.setBook(book);
        borrowHistory.setStatus(BookStatus.BORROWED.name());
        borrowHistory.setBorrowDate(LocalDate.now());
        borrowHistory.setReturnDate(request.getReturnDate());
        borrowHistoryRepository.save(borrowHistory);
    }

    void validateBorrowBook(BorrowBookRequest request, Borrower borrower, Book book){
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

}
