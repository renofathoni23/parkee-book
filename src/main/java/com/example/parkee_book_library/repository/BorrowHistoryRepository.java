package com.example.parkee_book_library.repository;

import com.example.parkee_book_library.model.BorrowHistory;
import com.example.parkee_book_library.model.Borrower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, String> {
    @Query("SELECT bh FROM BorrowHistory bh where bh.borrower =:borrower and bh.status =:status")
    Optional<BorrowHistory> findByBorrowerAndStatus(@Param("borrower") Borrower borrower, @Param("status") String status);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM borrow_history bh where bh.borrower_id = :borrowerId AND bh.status = :status)", nativeQuery = true)
    boolean existByBorrowerIdAndStatus(@Param("borrowerId") Long borrowerId, @Param("status") String status);
    @Query(value = "Select bh from BorrowHistory bh where UPPER(bh.status) = UPPER(:status)")
    Page<BorrowHistory>findByStatusPageable(@Param("status") String status, Pageable pageable);
}
