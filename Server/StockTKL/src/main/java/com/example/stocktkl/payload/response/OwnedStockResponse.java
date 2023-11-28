package com.example.stocktkl.payload.response;
import com.example.stocktkl.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OwnedStockResponse {
    private Stock stock;
    private Integer quantity;
}