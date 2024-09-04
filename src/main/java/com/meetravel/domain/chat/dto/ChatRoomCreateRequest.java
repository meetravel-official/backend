package com.meetravel.domain.chat.dto;

import com.meetravel.domain.chat.entity.ChatRoom;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import jakarta.validation.constraints.NotBlank;

public record ChatRoomCreateRequest(
    @NotBlank
    String title,
    MatchingFormEntity matchingFormEntity
) {
    public ChatRoom toEntity() {
        return ChatRoom.builder()
            .title(title)
            .matchingFormEntity(matchingFormEntity)
            .build();
    }
}
