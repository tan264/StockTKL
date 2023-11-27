package com.example.stocktkl.repository;

import com.example.stocktkl.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Tan Dang
 * @since 25/11/2023 - 9:23 pm
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByStockIdAndUserId(Long stockId, Long userId);
}
