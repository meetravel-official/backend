package com.meetravel.global.websocket.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rabbitmq.relay")
public class RabbitMQRelayProperties {
    private String host;
    private int port;
}
