package com.example.stocktkl.repository;

import com.example.stocktkl.model.Stock;
import com.example.stocktkl.model.User;
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

    @Query("SELECT DISTINCT p.stock FROM Portfolio p WHERE p.user = :user")
    List<Stock> findOwnedStocksByUser(@Param("user") User user);

}
