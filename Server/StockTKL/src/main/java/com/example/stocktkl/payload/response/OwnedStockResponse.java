package com.example.stocktkl.payload.response;
import com.example.stocktkl.model.Stock;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class OwnedStockResponse {
    private Long stockId;
    private String symbol;
    private String companyName;
    private String industry;
    private String sector;
    private Integer quantity;
    private LocalDateTime purchaseDate;
    private BigDecimal price;
}