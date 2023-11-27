package com.example.stocktkl.repository;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderStatus;
import com.example.stocktkl.model.enum_class.ERole;
import com.example.stocktkl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    @Query("SELECT q.price FROM Order o " +
            "JOIN o.stock s " +
            "JOIN s.quotes q " +
            "WHERE s.symbol = :symbol " +
            "ORDER BY q.timeStamp DESC " +
            "LIMIT 1")
    BigDecimal getPrice(String symbol);
    List<Order> findAllByDirectionAndStatusOrderByPriceDesc(EOrderDirection direction, EOrderStatus status);
    List<Order> findAllByDirectionAndStatusOrderByPriceAsc(EOrderDirection direction, EOrderStatus status);
    List<Order> findByUser(User user);
}

