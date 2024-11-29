package com.example.parkee_book_library.repository;

import com.example.parkee_book_library.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    @Query("SELECT br FROM Borrower br where br.ktpNumber =:ktpNumber")
    Optional<Borrower>findByKtpNumber(@Param("ktpNumber") String ktpNumber);
}
