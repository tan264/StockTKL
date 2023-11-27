package com.example.stocktkl.service;

import com.example.stocktkl.model.Order;

/**
 * @author Tan Dang
 * @since 25/11/2023 - 9:20 am
 */
public interface IOrderService {
    boolean sendToJadeRabbit(Order order);

    boolean canExecuteSellRequest(Long userId, Long stockId, Integer quantity);

    Long getStockIdBySymbol(String symbol);
}
