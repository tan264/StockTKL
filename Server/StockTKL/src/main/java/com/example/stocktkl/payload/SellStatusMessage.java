package com.example.stocktkl.payload;

import com.example.stocktkl.model.enum_class.EOrderStatus;
import com.example.stocktkl.payload.request.SellRequest;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@ToString
public class SellStatusMessage {

    private SellRequest request;
    private EOrderStatus orderStatus;

    public SellStatusMessage(SellRequest request, EOrderStatus orderStatus) {
        this.request = request;
        this.orderStatus = orderStatus;
    }

}
