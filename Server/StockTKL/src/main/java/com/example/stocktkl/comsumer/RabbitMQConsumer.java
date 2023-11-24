package com.example.stocktkl.comsumer;

import com.example.stocktkl.payload.SellStatusMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = {"${rabbitmq.buyLimitQueue.name}"})
    public void consumeMessageFromQueue(SellStatusMessage sellStatusMessage) {
        System.out.println("Message recieved from queue : " + sellStatusMessage);
    }
}
