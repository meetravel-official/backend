package com.meetravel.domain.chatroom.vo;

import com.meetravel.domain.matching_form.enums.Gender;
import com.meetravel.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatRoomPerson {
    @Schema(description = "채팅방 총합 인원 수")
    private final int totalCount;
    @Schema(description = "채팅방 여성 인원 수")
    private final int femaleCount;
    @Schema(description = "채팅방 남성 인원 수")
    private final int maleCount;

    public ChatRoomPerson(List<UserEntity> userEntities) {
        this.totalCount = userEntities.size();
        this.femaleCount = userEntities.stream()
                .filter(it -> Gender.FEMALE.equals(it.getGender()))
                .toList().size();
        this.maleCount = userEntities.stream()
                .filter(it -> Gender.MALE.equals(it.getGender()))
                .toList().size();
    }
}
