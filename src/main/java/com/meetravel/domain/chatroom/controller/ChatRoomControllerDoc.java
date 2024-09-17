package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.dto.CreateChatRoomRequest;
import com.meetravel.domain.chatroom.dto.CreateChatRoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings(value = "unused")
@Tag(name = "채팅방")
public interface ChatRoomControllerDoc {
    @Operation(
            summary = "채팅방 생성",
            description = """
                    [operation]
                    - 채팅방을 생성합니다.

                    [validation]
                    - 존재하지 않는 회원은 채팅방에 참여할 수 없습니다.
                    - 존재하지 않는 매칭 신청서로 채팅방에 참여할 수 없습니다.
                    - 이미 같은 매칭 신청서로 참여된 채팅방이 존재하면 새로운 채팅방에 참여할 수 없습니다.
                    """
    )
    ResponseEntity<CreateChatRoomResponse> createChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateChatRoomRequest request
    );
}
