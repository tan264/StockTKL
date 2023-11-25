package com.example.stocktkl.repository;

import com.example.stocktkl.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Tan Dang
 * @since 25/11/2023 - 5:05 pm
 */
@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query("SELECT q FROM Quote q " +
            "WHERE q.stockId = :stockIdParam " +
            "AND q.timeStamp >= (SELECT MAX(q2.timeStamp) FROM Quote q2 WHERE q2.stockId = :stockIdParam)")
    Optional<Quote> findTheNewestQuote(@Param("stockIdParam")Long stockId);
}
