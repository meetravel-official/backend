package com.meetravel.domain.chatroom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
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
        CompletableFuture<String> messageFuture = new CompletableFuture<>();

        StompHeaders headers = new StompHeaders();
        headers.setDestination("/app/chat/join");
        headers.add("chatRoomId", "1");

        StompSession session = stompClient.connect(
                "ws://localhost:" + port + "/ws/chat",
                new WebSocketHttpHeaders(),
                headers,
                new StompSessionHandlerAdapter(){}
        ).get(5, TimeUnit.SECONDS);

        session.subscribe("/chat/exchange/chat-rooms/1", new StompSessionHandlerAdapter() {
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                messageFuture.complete((String) payload);
            }
        });

        session.send("/chat/exchange/chat-rooms/1", "");

        String receivedMessage = messageFuture.get(2, TimeUnit.SECONDS);

        assertThat(receivedMessage).isEqualTo("채지원님이 채팅방에 입장하셨습니다.");
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