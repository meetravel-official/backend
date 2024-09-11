package com.meetravel.domain.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String CHAT_QUEUE_NAME;

    @Value("${rabbitmq.exchange.name}")
    private String CHAT_EXCHANGE_NAME;

    @Value("${rabbitmq.routing.key}")
    private String CHAT_ROUTING_KEY;

    @Value("${spring.rabbitmq.username}")
    private String rabbitUser;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitPort;

    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE_NAME, true);
    }

    @Bean
    public Exchange chatExchange() {
        return new TopicExchange(CHAT_EXCHANGE_NAME);
    }

    @Bean
    public Binding chatBinding(Queue chatQueue, Exchange chatExchange) {
        return BindingBuilder
            .bind(chatQueue)
            .to(chatExchange)
            .with(CHAT_ROUTING_KEY)
            .noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * RabbitMQ message를 JSON 형태로 주고 받기 위함
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        // LocalDateTime serializable 위함
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.registerModule(new JavaTimeModule());

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        return converter;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(rabbitHost);
        factory.setPort(rabbitPort);
        factory.setUsername(rabbitUser);
        factory.setPassword(rabbitPassword);
        factory.setVirtualHost("/");
        return factory;
    }
}
