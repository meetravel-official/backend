package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
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
                    - 작성된 매칭 신청서를 기반으로 여행 계획서를 생성합니다.

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
                    - 존재하지 않는 매칭 신청서로 채팅방에 참여할 수 없습니다.
                    - 이미 참여중인 채팅방에 다시 참여할 수 없습니다.
                    - 이미 참여중인 채팅방이 존재하면 새로운 채팅방에 참여할 수 없습니다.
                    - 이미 같은 매칭 신청서로 참여된 채팅방이 존재하면 새로운 채팅방에 참여할 수 없습니다.
                    - 정원 모집이 완료된 채팅방에는 참여할 수 없습니다.
                    """
    )
    ResponseEntity<Object> joinChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @Schema(description = "채팅방 고유 번호")
            @PathVariable Long chatRoomId,
            @RequestBody JoinChatRoomRequest request
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

    @Operation(
            summary = "채팅방 검색",
            description = """
                    [operation]
                    - 조건에 맞는 채팅방을 검색합니다.
                    
                    [condition]
                    - 매칭 신청서가 존재하지 않는 채팅방은 검색에서 제외됩니다.
                    - 내가 입장 했었던 채팅방은 검색에서 제외됩니다.
                    - 지역 대분류와 일치하는 채팅방을 검색합니다.
                      단, 지역 대분류가 전국이라면 지역 검색을 스킵합니다.
                    - 검색 키워드와 지역 대분류가 부분 일치하는 채팅방을 검색합니다.
                    
                    [sorting]
                    - 최신순(default):
                      채팅방이 생성된 일시 순서로 정렬됩니다.
                    - 빠른 출발순:
                      여행 시작 일자가 가장 가까운 순서로 정렬됩니다.
                    - 남은 정원이 가장 적은 순서로 정렬됩니다.
                    - 채팅방이 생성된 순서로 정렬됩니다.
                    """
    )
    ResponseEntity<SearchChatRoomResponse> searchChatRooms(
            UserDetails userDetails,
            SearchChatRoomRequest request
    );

    @Operation(
            summary = "실시간 여행 매칭",
            description = """
                    [operation]
                    - 가장 최근에 매칭이 시작된 순서로 9개의 채팅방을 보여줍니다.
                    - 내가 입장했던 채팅방은 제외하고 보여집니다.
                    """
    )
    ResponseEntity<SearchLiveChatRoomResponse> searchLiveChatRooms(@AuthenticationPrincipal UserDetails userDetails);

    @Operation(
            summary = "채팅방 정보 조회",
            description = """
                    [operation]
                    - 입장한 채팅방의 입장 인원과 여행 정보를 보여줍니다.
                    
                    [validation]
                    - 존재하지 않는 채팅방의 인원 목록은 볼 수 없습니다.
                    - 입장하지 않은 채팅방의 인원 목록은 볼 수 없습니다.
                    """
    )
    ResponseEntity<GetChatRoomResponse> getChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @Schema(description = "채팅방 고유 번호")
            @PathVariable Long chatRoomId
    );

    @Operation(
            summary = "채팅방 메세지 조회",
            description = """
                    [operation]
                    - 입장한 채팅방의 메세지를 보여줍니다.
                    
                    [validation]
                    - 존재하지 않는 채팅방의 메세지는 볼 수 없습니다.
                    - 입장하지 않은 채팅방의 메세지는 볼 수 없습니다.
                    """
    )
    ResponseEntity<Page<ChatMessage>> getChatMessages(
            @AuthenticationPrincipal UserDetails userDetails,
            @Schema(description = "채팅방 고유 번호")
            @PathVariable Long chatRoomId,
            @ParameterObject
            GetChatMessageRequest request
    );
}
