package com.example.stocktkl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Tan Dang
 * @since 17/11/2023 - 10:54 pm
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RealtimeQuote {
    private String symbol;
    private String companyName;
    private String industry;
    private String sector;
    private BigDecimal price;
    private BigDecimal changeValue;
    private BigDecimal percentChange;
    private int volume;
}
