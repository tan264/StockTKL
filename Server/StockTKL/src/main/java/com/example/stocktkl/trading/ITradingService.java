package com.example.stocktkl.trading;

import com.example.stocktkl.model.Order;

/**
 * @author Tan Dang
 * @since 25/11/2023 - 9:33 pm
 */
public interface ITradingService {

    void handleBuyRequest(Order order);

    void handleSellRequest(Order order);
}
