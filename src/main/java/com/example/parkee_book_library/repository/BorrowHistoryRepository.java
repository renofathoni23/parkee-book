package com.example.parkee_book_library.repository;

import com.example.parkee_book_library.model.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, String> {
}
