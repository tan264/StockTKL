package com.example.stocktkl.payload.request;

import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
//    private Long userId;
    private String stockSymbol;
    private EOrderType orderType;
    private EOrderDirection direction;
    private int quantity;
    private BigDecimal price = BigDecimal.valueOf(-1);
}
