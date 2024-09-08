package com.meetravel.domain.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.username}")
    private String rabbitUser;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitPort;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setPathMatcher(new AntPathMatcher(".")); // ex) url이 chat/room/3 이면, chat.room.3으로 바꿔줌.
        config.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
            .setClientLogin(rabbitUser)
            .setClientPasscode(rabbitPassword)
            .setSystemLogin(rabbitUser)
            .setSystemPasscode(rabbitPassword)
            .setRelayHost(rabbitHost)
            .setRelayPort(rabbitPort);

        // 클라이언트로부터 받을 메시지 prefix
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // socketJS 클라이언트가 WebSocket 핸드셰이크를 하기 위해 연결할 endpoint를 지정할 수 있다.
        registry.addEndpoint("/chat/inbox")
            .setAllowedOriginPatterns("*"); // cors 혀용을 위한 필수 설정.
    }
}
