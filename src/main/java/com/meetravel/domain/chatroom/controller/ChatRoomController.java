package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.dto.*;
import com.meetravel.domain.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chat-rooms")
@RestController
@RequiredArgsConstructor
public class ChatRoomController implements ChatRoomControllerDoc {
    private final ChatRoomService chatRoomService;

    @Override
    @PostMapping
    public ResponseEntity<CreateChatRoomResponse> createChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateChatRoomRequest request
    ) {
        CreateChatRoomResponse response = chatRoomService.createChatRoom(
                userDetails.getUsername(),
                request.getMatchingFormId()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/join/{chatRoomId}")
    public ResponseEntity<Object> joinChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long chatRoomId,
            @RequestBody(required = false) JoinChatRoomRequest request
    ) {
        chatRoomService.joinChatRoom(
                userDetails.getUsername(),
                chatRoomId,
                request == null ? null : request.getMatchingFormId()
        );

        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/leave/{chatRoomId}")
    public ResponseEntity<Object> leaveChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long chatRoomId
    ) {
        chatRoomService.leaveChatRoom(
                userDetails.getUsername(),
                chatRoomId
        );

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<GetMyChatRoomResponse> getMyChatRooms(@AuthenticationPrincipal UserDetails userDetails) {
        GetMyChatRoomResponse response = chatRoomService.getMyChatRooms(userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/search/live")
    public ResponseEntity<SearchLiveChatRoomResponse> searchLiveChatRooms(@AuthenticationPrincipal UserDetails userDetails) {
        SearchLiveChatRoomResponse response = chatRoomService.searchLiveChatRooms(userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<GetChatRoomResponse> getChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long chatRoomId
    ) {
        GetChatRoomResponse response = chatRoomService.getChatRoom(
                userDetails.getUsername(),
                chatRoomId
        );

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<Page<ChatMessage>> getChatMessages(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long chatRoomId,
            @ParameterObject
            GetChatMessageRequest request
    ) {
        Page<ChatMessage> response = chatRoomService.getChatMessages(
                userDetails.getUsername(),
                chatRoomId,
                request
        );

        return ResponseEntity.ok(response);
    }
}
