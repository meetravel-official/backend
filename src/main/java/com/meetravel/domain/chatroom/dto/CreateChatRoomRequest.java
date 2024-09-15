package com.meetravel.domain.chatroom.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateChatRoomRequest {
    @Schema(
            description = "매칭 신청서 고유 번호",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private final Long matchingFormId;

    public CreateChatRoomRequest(
            Long matchingFormId
    ) {
        this.matchingFormId = matchingFormId;
    }
}