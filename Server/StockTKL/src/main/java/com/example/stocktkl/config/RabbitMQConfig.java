package com.example.stocktkl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.sellQueue.name}")
    private String sellQueue;

    @Value("${rabbitmq.buyQueue.name}")
    private String buyQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.sellRouting.key}")
    private String sellRoutingKey;

    @Value("${rabbitmq.buyRouting.key}")
    private String buyRoutingKey;

    @Bean
    public Queue sellQueue() {
        return new Queue(sellQueue);
    }

    @Bean
    public Queue buyQueue() {
        return new Queue(buyQueue);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding sellBinding(Queue sellQueue, DirectExchange exchange) {
        return BindingBuilder.bind(sellQueue).to(exchange).with(sellRoutingKey);
    }

    @Bean
    public Binding buyBinding(Queue buyQueue, DirectExchange exchange) {
        return BindingBuilder.bind(buyQueue).to(exchange).with(buyRoutingKey);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter(objectMapper());
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}