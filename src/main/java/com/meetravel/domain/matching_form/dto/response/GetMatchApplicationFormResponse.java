package com.meetravel.domain.matching_form.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetMatchApplicationFormResponse {
    @Schema(
            description = "매칭 신청서 고유 번호",
            nullable = true
    )
    private final Long matchingFormId;
    @Schema(
            description = "매칭된 채팅방 고유 번호",
            nullable = true
    )
    private final Long chatRoomId;

    public GetMatchApplicationFormResponse() {
        this.matchingFormId = null;
        this.chatRoomId = null;
    }

    public GetMatchApplicationFormResponse(Long matchingFormId, Long chatRoomId) {
        this.matchingFormId = matchingFormId;
        this.chatRoomId = chatRoomId;
    }
}
