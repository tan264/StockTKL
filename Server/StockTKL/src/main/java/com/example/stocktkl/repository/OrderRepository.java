package com.example.stocktkl.repository;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.enum_class.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    @Query("SELECT q.price FROM Order o " +
            "JOIN o.stock s " +
            "JOIN s.quotes q " +
            "WHERE s.symbol = :symbol " +
            "ORDER BY q.timeStamp DESC " +
            "LIMIT 1")
    BigDecimal getPrice(String symbol);
}

