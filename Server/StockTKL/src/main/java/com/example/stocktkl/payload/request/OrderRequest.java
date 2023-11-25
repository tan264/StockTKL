package com.example.stocktkl.payload.request;

import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long userId;
    private String stockSymbol;
    private EOrderType orderType;
    private EOrderDirection direction;
    private int quantity;
    private int price = -1;
}
