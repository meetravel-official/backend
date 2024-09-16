package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.dto.ChatMessage;
import com.meetravel.domain.chatroom.enums.ChatMessageType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatRoomSocketControllerTest {
    private final int port;
    private final WebSocketStompClient stompClient;

    @Test
    public void userCanReceiveEnterMessageUponJoiningChatRoom() throws Exception {
        CompletableFuture<StompSession> sessionFuture = new CompletableFuture<>();
        CompletableFuture<ChatMessage> messageFuture = new CompletableFuture<>();

        StompSessionHandlerAdapter sessionHandlerAdapter = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                sessionFuture.complete(session);
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                messageFuture.complete((ChatMessage) payload);
            }
        };

        StompSession session = stompClient.connectAsync(
                "ws://localhost:" + port + "/ws/chat",
                sessionHandlerAdapter
        ).get(100, TimeUnit.SECONDS);

        session.send("/app/join", new ChatMessage(ChatMessageType.JOIN, 1L, "3705264650@kakao", null));

        session.subscribe("/topic/chat-rooms/1", new StompSessionHandlerAdapter() {
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                messageFuture.complete((ChatMessage) payload);
            }
        });

        ChatMessage receivedMessage = messageFuture.get(2, TimeUnit.SECONDS);

        assertThat(receivedMessage.getMessage()).isEqualTo("채지원님이 채팅방에 입장하셨습니다.");
    }

    public ChatRoomSocketControllerTest(
            @LocalServerPort
            int port
    ) {
        this.port = port;
        this.stompClient = new WebSocketStompClient(new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }
}