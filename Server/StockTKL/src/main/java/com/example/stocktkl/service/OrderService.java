package com.example.stocktkl.service;

import com.example.stocktkl.payload.request.LimitBuyRequest;
import com.example.stocktkl.payload.request.MarketBuyRequest;
import com.example.stocktkl.payload.request.SellRequest;
import com.example.stocktkl.payload.response.OrderResponse;

import java.math.BigDecimal;

public interface OrderService {
    public BigDecimal getMarketPrice(String symbol) ;
    public void processLimitBuy(LimitBuyRequest request) ;
    public OrderResponse executeMarketBuy(MarketBuyRequest request) ;

    public void processSell(SellRequest request);
}
