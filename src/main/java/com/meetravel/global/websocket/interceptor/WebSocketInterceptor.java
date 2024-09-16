package com.meetravel.global.websocket.interceptor;

import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.UnAuthorizedException;
import com.meetravel.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
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

            if (token == null || jwtService.getIsTemporary(token)) {
                throw new UnAuthorizedException(ErrorCode.NOT_TEMPORARY_TOKEN_ALLOWED_URL_EXCEPTION.getMessage());
            }

            Authentication authentication = jwtService.getAuthentication(token, false);
            accessor.setUser(authentication);
        }

        return message;
    }
}
