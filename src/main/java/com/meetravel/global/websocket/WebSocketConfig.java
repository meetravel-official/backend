package com.meetravel.global.websocket;

import com.meetravel.global.websocket.interceptor.WebSocketInterceptor;
import com.meetravel.global.websocket.properties.RabbitMQProperties;
import com.meetravel.global.websocket.properties.RabbitMQStompProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final RabbitMQProperties rabbitMQProperties;
    private final RabbitMQStompProperties rabbitMQStompProperties;
    private final WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns(
                        "http://localhost:*",
                        "https://cdiptangshu.github.io"
                )
                .withSockJS(); // sock.js 미사용 시 비활성화
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .setPathMatcher(new AntPathMatcher("."))
                .enableStompBrokerRelay("/queue", "/topic", "/exchange")
                .setRelayHost(rabbitMQProperties.getHost())
                .setRelayPort(rabbitMQStompProperties.getPort())
                .setClientLogin(rabbitMQProperties.getUsername())
                .setClientPasscode(rabbitMQProperties.getPassword())
                .setSystemLogin(rabbitMQProperties.getUsername())
                .setSystemPasscode(rabbitMQProperties.getPassword());

        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketInterceptor);
    }
}
