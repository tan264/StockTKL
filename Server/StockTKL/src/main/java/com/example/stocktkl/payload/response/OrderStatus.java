package com.example.stocktkl.payload.response;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.payload.request.OrderRequest;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderStatus {

    private OrderRequest order;
    private String status;
    private String message;
}
