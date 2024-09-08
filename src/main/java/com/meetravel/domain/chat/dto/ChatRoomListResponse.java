package com.meetravel.domain.chat.dto;

import com.meetravel.domain.chat.entity.ChatRoom;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ChatRoomListResponse(
    List<ChatRoomResponse> chatRoomList
) {

    public static ChatRoomListResponse fromChatRoomList(List<ChatRoom> chatRoomList) {
        List<ChatRoomResponse> result = chatRoomList.stream()
            .map(ChatRoomResponse::fromChatRoom)
            .collect(Collectors.toList());

        return ChatRoomListResponse.builder()
            .chatRoomList(result)
            .build();
    }
}
