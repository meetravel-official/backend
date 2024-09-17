package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class CreateChatRoomResponse {
    @Schema(description = "채팅방 고유 번호")
    private final Long chatRoomId;
    @Schema(description = "채팅방 생성 일시", format = "yyyy-MM-ddTHH:mm:ss")
    private final String createdAt;

    public CreateChatRoomResponse(ChatRoomEntity chatRoomEntity) {
        this.chatRoomId = chatRoomEntity.getId();
        this.createdAt = chatRoomEntity.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
