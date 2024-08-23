package com.example.stocktkl.repository;

import com.example.stocktkl.model.User;
import com.example.stocktkl.payload.response.OwnedStockResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT NEW com.example.stocktkl.payload.response.OwnedStockResponse(" +
            "p.stock.stockId, p.stock.symbol, p.stock.companyName, p.stock.industry, p.stock.sector, " +
            "p.quantity, p.purchaseDate, p.avgPurchasePrice) " +
            "FROM Portfolio p WHERE p.user = :user")
    List<OwnedStockResponse> findOwnedStocksByUser(@Param("user") User user);

}
