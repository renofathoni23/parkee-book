package com.example.parkee_book_library.repository;

import com.example.parkee_book_library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
