package com.example.stocktkl.service;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.Stock;
import java.util.List;

public interface UserService {
    List<Stock> getWatchlistedStocksForCurrentUser();
    List<Stock> getOwnedStocksForCurrentUser();

    List<Order> getOrdersForCurrentUser();
}
