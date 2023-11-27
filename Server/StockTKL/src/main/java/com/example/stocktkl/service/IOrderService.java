package com.example.stocktkl.service;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderType;

import java.math.BigDecimal;

/**
 * @author Tan Dang
 * @since 25/11/2023 - 9:20 am
 */
public interface IOrderService {
    boolean sendToJadeRabbit(Order order);

    boolean canExecuteSellRequest(Long userId, Long stockId, Integer quantity);

    Long getStockIdBySymbol(String symbol);

    boolean deleteOrder(Long userId, Long stockId, EOrderDirection direction, EOrderType orderType, BigDecimal price, Integer quantity);
}
