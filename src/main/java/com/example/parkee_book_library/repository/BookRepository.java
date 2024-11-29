package com.example.parkee_book_library.repository;

import com.example.parkee_book_library.model.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    @Query("select b from Book b where b.isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn")String isbn);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b from Book b where b.id = :bookId")
    Optional<Book> findByIdPessimisticLock(@Param("bookId") String bookId);
}
