package com.example.stocktkl.comsumer;

import com.example.stocktkl.payload.response.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {

//    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
//    public void consumeMessageFromQueue(OrderStatus orderStatus) {
//        System.out.println("Message recieved from queue : " + orderStatus);
//    }
}
