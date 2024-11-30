package com.example.parkee_book_library.service.impl;

import com.example.parkee_book_library.dto.CustomPageResponse;
import com.example.parkee_book_library.dto.request.BookRequest;
import com.example.parkee_book_library.exception.BusinessException;
import com.example.parkee_book_library.model.Book;
import com.example.parkee_book_library.repository.BookRepository;
import com.example.parkee_book_library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public void create(BookRequest request) {
        validateBook(request);
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setStock(request.getStock());
        bookRepository.save(book);
    }
    @Override
    public CustomPageResponse<Book> getData(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);
        CustomPageResponse<Book> customPageResponse = new CustomPageResponse<>();
        customPageResponse.setPage(pageable.getPageNumber() + 1);
        customPageResponse.setSize(pageable.getPageSize());
        customPageResponse.setTotalData(bookPage.getTotalElements());
        customPageResponse.setData(bookPage.getContent());
        return customPageResponse;
    }

    private void validateBook(BookRequest request){
        Optional<Book> optionalBook = bookRepository.findByIsbn(request.getIsbn());
        if(optionalBook.isPresent()){
            throw new BusinessException("Book is already exist");
        }
    };
}
