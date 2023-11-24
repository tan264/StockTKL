package com.example.stocktkl.payload.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyMarketRequest {

    @NotBlank
    private String symbol;

    @NotBlank
    private String companyName;

    @NotBlank
    private Integer quantity;

}