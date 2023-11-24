package com.example.stocktkl.payload.request;

import com.example.stocktkl.model.enum_class.EOrderType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellRequest {

    @NotBlank
    private String symbol;

    @NotBlank
    private String companyName;

    @NotBlank
    private Integer quantity;

    @NotBlank
    @DecimalMin("0.00")
    private BigDecimal price;

    private String industry;

    private String sector;

    private EOrderType orderType;

}
