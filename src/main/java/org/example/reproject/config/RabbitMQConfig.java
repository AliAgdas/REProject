package org.example.reproject.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    // FIFO Kuyruk
    public static final String FIFO_QUEUE_NAME = "fifoQueue";

    // Öncelikli Kuyruk
    public static final String PRIORITY_QUEUE_NAME = "priorityQueue";
    public static final String EXCHANGE_NAME = "priorityExchange";
    public static final String ROUTING_KEY = "priorityRoutingKey";

    @Bean
    public Queue fifoQueue() {
        return new Queue(FIFO_QUEUE_NAME, true, false, false);
    }

    @Bean
    public Queue priorityQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", 100);
        return new Queue(PRIORITY_QUEUE_NAME, true, false, false, args);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding priorityBinding(Queue priorityQueue, DirectExchange exchange) {
        return BindingBuilder.bind(priorityQueue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding fifoBinding(Queue fifoQueue, DirectExchange exchange) {
        return BindingBuilder.bind(fifoQueue).to(exchange).with("fifoRoutingKey");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}


