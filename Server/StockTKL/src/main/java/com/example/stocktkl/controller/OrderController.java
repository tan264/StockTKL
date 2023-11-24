package com.example.stocktkl.controller;

import com.example.stocktkl.payload.request.LimitBuyRequest;
import com.example.stocktkl.payload.request.SellRequest;
import com.example.stocktkl.service.impl.OrderServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }
    @PostMapping("/buy-limit")
    public void processBuyLimit(LimitBuyRequest request) {

        orderServiceImpl.processLimitBuy(request);
    }

    @PostMapping("/buy-market")
    public void processBuyMarket(LimitBuyRequest request) {

        orderServiceImpl.processLimitBuy(request);
    }

    @PostMapping("/sell")
    public void processSell(SellRequest request) {
        orderServiceImpl.processSell(request);
    }
}
