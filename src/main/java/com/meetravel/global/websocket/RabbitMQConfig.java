package com.meetravel.global.websocket;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfig {
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("chat.exchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("join");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with("join");
    }

    @Primary
    @Bean
    public ConnectionFactory rabbitMQConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(10301);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("#*eB@zd2qbuq6+F_<rJ$");

        return connectionFactory;
    }

    @Bean
    public ConnectionFactory rabbitMQAdminConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(10301);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("#*eB@zd2qbuq6+F_<rJ$");

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
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitMQAdminConnectionFactory());
        rabbitAdmin.declareExchange(directExchange());
        rabbitAdmin.declareQueue(queue());
        rabbitAdmin.declareBinding(binding());

        return rabbitAdmin;
    }
}
