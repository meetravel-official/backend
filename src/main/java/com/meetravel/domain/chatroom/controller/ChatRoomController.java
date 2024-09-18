package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.dto.CreateChatRoomRequest;
import com.meetravel.domain.chatroom.dto.CreateChatRoomResponse;
import com.meetravel.domain.chatroom.dto.GetMyChatRoomResponse;
import com.meetravel.domain.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
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
            @PathVariable Long chatRoomId
    ) {
        chatRoomService.joinChatRoom(
                userDetails.getUsername(),
                chatRoomId
        );

        return ResponseEntity.noContent().build();
    }

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

    @GetMapping
    public ResponseEntity<GetMyChatRoomResponse> getMyChatRooms(@AuthenticationPrincipal UserDetails userDetails) {
        GetMyChatRoomResponse response = chatRoomService.getMyChatRooms(userDetails.getUsername());

        return ResponseEntity.ok(response);
    }
}
