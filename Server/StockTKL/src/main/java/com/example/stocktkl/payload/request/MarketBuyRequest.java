package com.example.stocktkl.payload.request;

import com.example.stocktkl.model.enum_class.EOrderType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketBuyRequest {

    @NotBlank
    private String symbol;

    @NotBlank
    private String companyName;

    @NotBlank
    private Integer quantity;

    private String industry;

    private String sector;

    private EOrderType orderType;
}