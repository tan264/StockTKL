package com.example.stocktkl.payload.response;

import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderStatus;
import com.example.stocktkl.model.enum_class.EOrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String symbol;
    private String companyName;
    private String industry;
    private String sector;
    private BigDecimal price ;
    private Integer quantity;
    private EOrderDirection direction;
    private EOrderStatus status;
    private EOrderType orderType;
    private LocalDateTime orderDate;
}
