package com.example.parkee_book_library.service.impl;

import com.example.parkee_book_library.dto.BaseResponse;
import com.example.parkee_book_library.dto.request.BookRequest;
import com.example.parkee_book_library.exception.BusinessException;
import com.example.parkee_book_library.model.Book;
import com.example.parkee_book_library.repository.BookRepository;
import com.example.parkee_book_library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private void validateBook(BookRequest request){
        Optional<Book> optionalBook = bookRepository.findByIsbn(request.getIsbn());
        if(optionalBook.isPresent()){
            throw new BusinessException("Book is already exist");
        }
    };
}
