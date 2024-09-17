package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.vo.ChatRoomPreviewInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class GetMyChatRoomResponse {
    @Schema(description = "채팅방 목록")
    private final List<ChatRoomPreviewInfo> chatRooms;

    public GetMyChatRoomResponse(List<ChatRoomPreviewInfo> chatRooms) {
        this.chatRooms = chatRooms;
    }
}
