package com.meetravel.domain.travel.controller;

import com.meetravel.domain.travel.dto.GetChatRoomTravelPlanResponse;
import com.meetravel.domain.travel.dto.ModifyDailyTravelPlanRequest;
import com.meetravel.domain.travel.dto.ModifyTravelPlanKeywordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Operation(
            summary = "여행 계획서 키워드 수정",
            description = """
                    [operation]
                    - 여행 계획서의 여행 키워드를 수정합니다.

                    [validation]
                    - 입장하지 않은 채팅방의 여행 키워드를 수정할 수 없습니다.
                    - 존재하지 않는 채팅방의 여행 키워드를 수정할 수 없습니다.
                    """
    )
    void modifyTravelPlanKeywords(
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "채팅방 고유 번호")
            @PathVariable Long chatRoomId,
            @RequestBody ModifyTravelPlanKeywordRequest request
    );

    @Operation(
            summary = "일별 여행 계획 수정",
            description = """
                    [operation]
                    - 여행 계획서의 일별 여행 계획을 수정합니다.

                    [validation]
                    - 입장하지 않은 채팅방의 여행 계획을 수정할 수 없습니다.
                    - 존재하지 않는 채팅방의 여행 계획을 수정할 수 없습니다.
                    - 공유하지 않은 여행 장소를 여행 계획에서 선택할 수 없습니다.
                    - 여행 장소는 장소 유형 별 두 개까지 선택할 수 있습니다.
                    """
    )
    void modifyDailyTravelPlans(
            UserDetails userDetails,
            @Parameter(description = "채팅방 고유 번호")
            Long chatRoomId,
            ModifyDailyTravelPlanRequest request
    );
}
