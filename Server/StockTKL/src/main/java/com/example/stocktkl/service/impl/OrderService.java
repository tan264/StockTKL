package com.example.stocktkl.service.impl;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.Portfolio;
import com.example.stocktkl.model.Stock;
import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.repository.OrderRepository;
import com.example.stocktkl.repository.PortfolioRepository;
import com.example.stocktkl.repository.StockRepository;
import com.example.stocktkl.service.IOrderService;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        if (order.getDirection() == EOrderDirection.BUY) {
            log.info("Send to BUY QUEUE: " + order);
            rabbitTemplate.convertAndSend(exchange, buyRoutingKey, order);
        } else {
            log.info("Send to SELL QUEUE: " + order);
            rabbitTemplate.convertAndSend(exchange, sellRoutingKey, order);
        }
        orderRepository.save(order);

        return true;
    }

    @Override
    public boolean canExecuteSellRequest(Long stockId, Integer quantity) {
        try {
            Portfolio portfolio = portfolioRepository.findByStockId(stockId).orElseThrow(
                    RuntimeException::new);
            if (portfolio.getQuantity() >= quantity) {
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
}
