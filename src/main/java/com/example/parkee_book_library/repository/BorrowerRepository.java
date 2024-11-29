package com.example.parkee_book_library.repository;

import com.example.parkee_book_library.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Integer> {
}
