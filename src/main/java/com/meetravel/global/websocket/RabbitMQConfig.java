package com.meetravel.global.websocket;

import com.meetravel.global.websocket.properties.RabbitMQAmqpProperties;
import com.meetravel.global.websocket.properties.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {
    private final RabbitMQProperties rabbitMQProperties;
    private final RabbitMQAmqpProperties rabbitMQAmqpProperties;

    @Bean
    public TopicExchange directExchange() {
        return new TopicExchange("chat.exchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("chat.queue", true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with("chat.rooms.*");
    }

    @Bean
    public ConnectionFactory rabbitMQConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMQProperties.getHost());
        connectionFactory.setPort(rabbitMQAmqpProperties.getPort());
        connectionFactory.setVirtualHost(rabbitMQAmqpProperties.getVirtualHost());
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());

        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitMQConnectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(directExchange().getName());

        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitMQConnectionFactory());
        rabbitAdmin.declareExchange(directExchange());
        rabbitAdmin.declareQueue(queue());
        rabbitAdmin.declareBinding(binding());

        return rabbitAdmin;
    }
}
