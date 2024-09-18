package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.dto.CreateChatRoomRequest;
import com.meetravel.domain.chatroom.dto.CreateChatRoomResponse;
import com.meetravel.domain.chatroom.dto.GetMyChatRoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(
            summary = "채팅방 입장",
            description = """
                    [operation]
                    - 채팅방에 입장합니다.
                    - 채팅방 입장 메세지를 발송합니다.

                    [validation]
                    - 존재하지 않는 회원은 채팅방에 참여할 수 없습니다.
                    - 존재하지 않는 채팅방에 참여할 수 없습니다.
                    - 이미 참여중인 채팅방에 다시 참여할 수 없습니다.
                    """
    )
    ResponseEntity<Object> joinChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @Schema(description = "채팅방 고유 번호")
            @PathVariable Long chatRoomId
    );

    @Operation(
            summary = "채팅방 퇴장",
            description = """
                    [operation]
                    - 입장중인 채팅방을 퇴장합니다.
                    - 채팅방 퇴장 메세지를 발송합니다.

                    [validation]
                    - 존재하지 않는 회원은 채팅방을 퇴장할 수 없습니다.
                    - 존재하지 않는 채팅방을 퇴장할 수 없습니다.
                    - 입장하지 않은 채팅방을 퇴장할 수 없습니다.
                    """
    )
    ResponseEntity<Object> leaveChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @Schema(description = "채팅방 고유 번호")
            @PathVariable Long chatRoomId
    );

    @Operation(
            summary = "내 채팅방 목록",
            description = """
                    [operation]
                    - 로그인 한 회원의 채팅방 목록을 보여줍니다.

                    [validation]
                    - 존재하지 않는 회원의 채팅방 목록은 볼 수 없습니다.
                    - 매칭 신청서가 연결되지 않은 채팅방은 볼 수 없습니다.
                    - 입장하지 않은 채팅방은 볼 수 없습니다.
                    """
    )
    ResponseEntity<GetMyChatRoomResponse> getMyChatRooms(@AuthenticationPrincipal UserDetails userDetails);
}
