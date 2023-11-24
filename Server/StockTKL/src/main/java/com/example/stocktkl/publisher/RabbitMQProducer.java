package com.example.stocktkl.publisher;

import com.example.stocktkl.payload.request.OrderRequest;
import com.example.stocktkl.payload.SellStatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.buyLimitRouting.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/{stockName}")
    public String bookOrder(OrderRequest order, String stockName) {
        SellStatusMessage sellStatusMessage = new SellStatusMessage();
        rabbitTemplate.convertAndSend(exchange, routingKey, sellStatusMessage);
        return "Success !!";
    }
}
