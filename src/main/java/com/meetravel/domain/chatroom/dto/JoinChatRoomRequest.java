package com.meetravel.domain.chatroom.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class JoinChatRoomRequest {
    @Schema(
            description = "매칭 신청서 고유 번호",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private final Long matchingFormId;

    @JsonCreator
    public JoinChatRoomRequest(Long matchingFormId) {
        this.matchingFormId = matchingFormId;
    }
}
