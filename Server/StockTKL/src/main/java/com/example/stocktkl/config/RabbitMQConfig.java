package com.example.stocktkl.config;
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

    @Value("${rabbitmq.buyLimitQueue.name}")
    private String buyQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.sellRouting.key}")
    private String sellRoutingKey;

    @Value("${rabbitmq.buyLimitRouting.key}")
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
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }


    @Bean
    public Binding sellBinding(Queue sellQueue, TopicExchange exchange) {
        return BindingBuilder.bind(sellQueue).to(exchange).with(sellRoutingKey);
    }

    @Bean
    public Binding buyBinding(Queue buyQueue, TopicExchange exchange) {
        return BindingBuilder.bind(buyQueue).to(exchange).with(buyRoutingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}