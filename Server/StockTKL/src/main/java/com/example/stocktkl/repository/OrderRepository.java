package com.example.stocktkl.repository;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT q.price FROM Order o " +
            "JOIN o.stock s " +
            "JOIN s.quotes q " +
            "WHERE s.symbol = :symbol " +
            "ORDER BY q.timeStamp DESC " +
            "LIMIT 1")
    BigDecimal getPrice(String symbol);

    List<Order> findAllByDirectionAndStatusOrderByPriceDesc(EOrderDirection direction, EOrderStatus status);
    List<Order> findAllByDirectionAndStatusOrderByPriceAsc(EOrderDirection direction, EOrderStatus status);
}

