package com.meetravel.domain.chat.controller;

import com.meetravel.domain.chat.dto.ChatRoomCreateRequest;
import com.meetravel.domain.chat.dto.ChatRoomResponse;
import com.meetravel.domain.chat.dto.ChatRoomUserListResponse;
import com.meetravel.domain.chat.service.ChatRoomService;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.aop.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public ResponseEntity<?> getAllChatRooms() {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms());
    }

    @PostMapping
    public ResponseEntity<?> createChatRoom(@UserSession UserEntity user, ChatRoomCreateRequest request) {
        ChatRoomResponse response = chatRoomService.createChatRoom(user, request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{chatRoomId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<?> getChatRoom(@PathVariable Long chatRoomId) {
        ChatRoomResponse response = chatRoomService.getChatRoomResponse(chatRoomId);
        return ResponseEntity.ok(response);
    }

    /**
     * 채팅방 참여 중인 유저 리스트 반환
     */
    @GetMapping("/{chatRoomId}/users")
    public ResponseEntity<?> getChatRoomUsers(@PathVariable Long chatRoomId) {
        ChatRoomUserListResponse response = chatRoomService.getUserList(chatRoomId);
        return ResponseEntity.ok(response);
    }

    /**
     * /{chatRoomId}/update-title?title=~~
     *
     * @param chatRoomId 제목 업데이트 할 채팅방 고유번호
     * @param title      업데이트 될 새 채팅방 제목
     * @return 성공시 200
     */
    @PutMapping("/{chatRoomId}/update-title")
    public ResponseEntity<Void> updateTitle(@PathVariable Long chatRoomId, @RequestParam(name = "title") String title) {
        chatRoomService.updateTitle(chatRoomId, title);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{chatRoomId}/add-user")
    public ResponseEntity<Void> addUser(@PathVariable Long chatRoomId, @RequestBody UserEntity user) {
        chatRoomService.addUser(chatRoomId, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{chatRoomId}/travel-plan")
    public ResponseEntity<?> getTravelPlan(@PathVariable Long chatRoomId) {
        // TODO: 여행 계획 나오면 추가해야 됨
        return null;
    }
}
