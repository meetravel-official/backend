package com.meetravel.global.websocket.interceptor;

import com.meetravel.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;

@Configuration
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = jwtService.getToken(accessor);

            if (token == null || !jwtService.validateToken(token)) {
                throw new MessageDeliveryException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            }

            if (jwtService.getIsTemporary(token)) {
                throw new MessageDeliveryException(HttpStatus.FORBIDDEN.getReasonPhrase());
            }

            Authentication authentication = jwtService.getAuthentication(token, false);
            accessor.setUser(authentication);
        }

        return message;
    }
}
