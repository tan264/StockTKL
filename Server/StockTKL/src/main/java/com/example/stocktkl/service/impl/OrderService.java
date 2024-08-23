package com.example.stocktkl.service.impl;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.Portfolio;
import com.example.stocktkl.model.Stock;
import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderStatus;
import com.example.stocktkl.model.enum_class.EOrderType;
import com.example.stocktkl.repository.OrderRepository;
import com.example.stocktkl.repository.PortfolioRepository;
import com.example.stocktkl.repository.StockRepository;
import com.example.stocktkl.service.IOrderService;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Tan Dang
 * @since 25/11/2023 - 9:29 am
 */
@Service
@Log
public class OrderService implements IOrderService {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.buyRouting.key}")
    private String buyRoutingKey;

    @Value("${rabbitmq.sellRouting.key}")
    private String sellRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    private final OrderRepository orderRepository;

    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;

    public OrderService(RabbitTemplate rabbitTemplate, OrderRepository orderRepository, PortfolioRepository portfolioRepository, StockRepository stockRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.orderRepository = orderRepository;
        this.portfolioRepository = portfolioRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public boolean sendToJadeRabbit(Order order) {
        Order savedOrder = orderRepository.save(order);
        if (order.getDirection() == EOrderDirection.BUY) {
            log.info("Send to BUY QUEUE: " + savedOrder);
            rabbitTemplate.convertAndSend(exchange, buyRoutingKey, savedOrder);
        } else {
            log.info("Send to SELL QUEUE: " + savedOrder);
            rabbitTemplate.convertAndSend(exchange, sellRoutingKey, savedOrder);
        }


        return true;
    }

    @Override
    public boolean canExecuteSellRequest(Long stockId, Long userId, Integer quantity) {
        try {
            Portfolio portfolios = portfolioRepository.findByStockIdAndUserId(stockId,
                    userId).orElseThrow(RuntimeException::new);
            int totalQuantity = portfolios.getQuantity();
            if (totalQuantity >= quantity) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public Long getStockIdBySymbol(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol).orElseThrow(RuntimeException::new);
        return stock.getStockId();
    }

    @Override
    public boolean deleteOrder(Long userId, Long stockId, EOrderDirection direction, EOrderType orderType, BigDecimal price, Integer quantity) {
        try {
            orderRepository.deleteByUserIdAndStockIdAndDirectionAndOrderTypeAndStatusAndPriceAndQuantity(
                    userId,
                    stockId,
                    direction,
                    orderType, EOrderStatus.PENDING, price, quantity);
            return true;
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
        return false;
    }
}
