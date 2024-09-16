package com.meetravel.global.websocket;

import com.meetravel.global.websocket.properties.RabbitMQRelayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final RabbitMQRelayProperties rabbitMQRelayProperties;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // sock.js 미사용 시 비활성화
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .setPathMatcher(new AntPathMatcher("."))
                .enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
                .setRelayHost(rabbitMQRelayProperties.getHost())
                .setRelayPort(rabbitMQRelayProperties.getPort())
                .setClientLogin("admin")
                .setClientPasscode("#*eB@zd2qbuq6+F_<rJ$")
                .setSystemLogin("admin")
                .setSystemPasscode("#*eB@zd2qbuq6+F_<rJ$");

        registry.setApplicationDestinationPrefixes("/pub");
    }
}
