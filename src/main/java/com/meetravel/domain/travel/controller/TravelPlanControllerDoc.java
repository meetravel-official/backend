package com.meetravel.domain.travel.controller;

import com.meetravel.domain.travel.dto.GetChatRoomTravelPlanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;

@SuppressWarnings(value = "unused")
@Tag(name = "여행")
public interface TravelPlanControllerDoc {
    @Operation(
            summary = "여행 계획서 조회",
            description = """
                    [operation]
                    - 여행 계획서를 조회합니다.

                    [validation]
                    - 입장하지 않은 채팅방의 여행 계획서를 조회할 수 없습니다.
                    - 존재하지 않는 채팅방의 여행 계획서를 조회할 수 없습니다.
                    """
    )
    ResponseEntity<GetChatRoomTravelPlanResponse> getTravelPlanWithChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "채팅방 고유 번호")
            @PathVariable Long chatRoomId
    );
}
