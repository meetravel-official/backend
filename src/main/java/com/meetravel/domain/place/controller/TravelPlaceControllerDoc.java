package com.meetravel.domain.place.controller;

import com.meetravel.domain.place.dto.GetTravelPlaceLikeCountListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@SuppressWarnings(value = "unused")
@Tag(name = "여행 장소(정보) API")
public interface TravelPlaceControllerDoc {

    @Operation(summary = "여행 장소(정보) 평가(하트)", description = "여행 장소(정보)를 평가(하트)합니다.")
    void review(@RequestParam String placeId);
    @Operation(
            summary = "여행 장소 공유하기",
            description = """
                    [operation]
                    - 현재 여행 진행 중인 채팅방에 여행 장소를 공유합니다.
                    - 이미 공유된 장소라면 액션 없이 넘어갑니다.

                    [validation]
                    - 현재 여행 진행 중인 채팅방이 없으면 장소를 공유할 수 없습니다.
                    - 존재하지 않는 장소를 공유할 수 없습니다.
                    """
    )
    ResponseEntity<Object> shareTravelPlace(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String placeId
    );
    @Operation(summary = "여행 장소(정보) 평가(하트) 취소", description = "여행 장소(정보) 평가(하트)를 취소합니다.")
    void cancelReview(@RequestParam String placeId);
    @Operation(summary = "여행 장소(정보)에 대한 하트 수 조회", description = "여행 장소(정보)에 대한 평가(하트) 수를 조회합니다.")
    ResponseEntity<GetTravelPlaceLikeCountListResponse> getTravelPlaceLikeCount(@RequestParam List<String> placeIds);
}
