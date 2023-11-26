package com.example.stocktkl.trading;

import com.example.stocktkl.model.Order;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Tan Dang
 * @since 25/11/2023 - 3:36 pm
 */
@Component
@Log
public class TradingGateway {

    private final ITradingService tradingService;

    public TradingGateway(ITradingService tradingService) {
        this.tradingService = tradingService;
    }

    @RabbitListener(queues = {"${rabbitmq.buyQueue.name}"})
    public void handleBuyRequest(Order order) {
        log.info("Message received from queue : " + order);
        try {
            tradingService.handleBuyRequest(order);
        } catch (Exception e) {
            e.printStackTrace();
            log.severe("Error processing buy request: " + e.getMessage());
        }
    }

    @RabbitListener(queues = {"${rabbitmq.sellQueue.name}"})
    public void handleSellRequest(Order order) {
        log.info("Message received from queue : " + order);
        try {
            tradingService.handleSellRequest(order);
        } catch (Exception e) {
            log.severe("Error processing sell request: " + e.getMessage());
        }
    }
}
