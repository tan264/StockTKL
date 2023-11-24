package com.example.stocktkl.payload.response;

import com.example.stocktkl.model.enum_class.EOrderStatus;
import com.example.stocktkl.payload.request.OrderRequest;
import com.example.stocktkl.payload.request.SellRequest;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@ToString
public class OrderStatusMessage {

    private SellRequest request;
    private EOrderStatus orderStatus;

    public OrderStatusMessage(SellRequest request, EOrderStatus orderStatus) {
        this.request = request;
        this.orderStatus = orderStatus;
    }

}
