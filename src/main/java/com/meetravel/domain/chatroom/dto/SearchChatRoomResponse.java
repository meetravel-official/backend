package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.vo.ChatRoomPreviewInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class SearchChatRoomResponse {
    @Schema(description = "채팅방 목록")
    private final Page<ChatRoomPreviewInfo> chatRooms;

    public SearchChatRoomResponse(Page<ChatRoomPreviewInfo> chatRooms) {
        this.chatRooms = chatRooms;
    }
}