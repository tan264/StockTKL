package com.example.stocktkl.controller;

import com.example.stocktkl.payload.request.BuyRequest;
import com.example.stocktkl.payload.request.SellRequest;
import com.example.stocktkl.service.impl.OrderServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderConsumer {

    private final OrderServiceImpl orderServiceImpl;

    public OrderConsumer(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }
    @RabbitListener(queues = {"${rabbitmq.buyLimitQueue.name}"})
    public void processBuyLimitOrder(BuyRequest request) {
        BigDecimal marketPrice = orderServiceImpl.getMarketPrice(request.getSymbol());

        orderServiceImpl.processLimitBuy(request, marketPrice);
    }

    @RabbitListener(queues = {"${rabbitmq.sellQueue.name}"})
    public void processSell(SellRequest request) {

        orderServiceImpl.processSell(request);
    }
}
