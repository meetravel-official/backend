package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.vo.ChatRoomPerson;
import com.meetravel.domain.chatroom.vo.ChatRoomUserPreviewInfo;
import com.meetravel.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class GetUserListChatRoomResponse {
    @Schema(description = "채팅방 입장 인원 정보")
    private final List<ChatRoomUserPreviewInfo> users;
    @Schema(description = "채팅방 입장 인원 수")
    private final ChatRoomPerson persons;

    public GetUserListChatRoomResponse(List<UserEntity> userEntities) {
        this.users = userEntities.stream()
                .map(ChatRoomUserPreviewInfo::new)
                .toList();
        this.persons = new ChatRoomPerson(userEntities);
    }
}
