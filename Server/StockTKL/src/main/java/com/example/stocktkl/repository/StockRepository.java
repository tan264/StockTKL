package com.example.stocktkl.repository;

import com.example.stocktkl.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("SELECT s, q " +
            "FROM Stock s " +
            "JOIN s.quotes q " +
            "WHERE q.timeStamp = (SELECT MAX(q2.timeStamp) FROM Quote q2 WHERE q2.stock = s) " +
            "ORDER BY q.timeStamp DESC")
    List<Object[]> getStockWithLatestQuote();
}
