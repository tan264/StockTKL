package com.example.stocktkl.service;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.Stock;
import com.example.stocktkl.payload.response.OrderResponse;
import com.example.stocktkl.payload.response.OwnedStockResponse;

import java.util.List;

public interface UserService {
    List<Stock> getWatchlistedStocksForCurrentUser();
    List<OwnedStockResponse> getOwnedStocksForCurrentUser();
    List<OrderResponse> getOrdersForCurrentUser();
    boolean addStockToWatchlist(String symbol);
    boolean removeStockFromWatchlist(String symbol);
}
