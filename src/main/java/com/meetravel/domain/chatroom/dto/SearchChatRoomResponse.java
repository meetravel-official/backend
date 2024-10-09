package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.vo.ChatRoomPreviewInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchChatRoomResponse {
    @Schema(description = "채팅방 목록")
    private final List<ChatRoomPreviewInfo> chatRooms;

    public SearchChatRoomResponse(List<ChatRoomPreviewInfo> chatRooms) {
        this.chatRooms = chatRooms;
    }
}