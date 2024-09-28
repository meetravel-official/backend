package com.meetravel.domain.matching_form.controller;

import com.meetravel.domain.matching_form.dto.request.CreateMatchingFormRequest;
import com.meetravel.domain.matching_form.dto.request.UpdateMatchingFormRequest;
import com.meetravel.domain.matching_form.dto.response.GetAreaResponse;
import com.meetravel.domain.matching_form.dto.response.GetDetailAreaResponse;
import com.meetravel.domain.matching_form.dto.response.GetMatchApplicationFormResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "매칭 신청서 API")
public interface MatchingFormControllerDoc {

    @Operation(summary = "매칭 신청서 작성", description = "매칭 신청서를 작성합니다.")
    void createMatchingForm(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestBody @Valid CreateMatchingFormRequest request);

    @Operation(summary = "매칭 신청서 수정", description = "매칭 신청서를 수정합니다.")
    void updateMatchingForm(@RequestBody @Valid UpdateMatchingFormRequest request);
    @Operation(summary = "매칭 신청서에서 선택할 지역(대분류) 목록 조회", description = "매칭 신청서에서 선택할 지역(대분류) 목록을 조회합니다.")
    GetAreaResponse getAreaResponse();

    @Operation(summary = "매칭 신청서에서 선택할 지역(소분류) 목록 조회", description = "매칭 신청서에서 선택할 지역(소분류) 목록을 조회합니다.")
    GetDetailAreaResponse getDetailAreaResponse(@RequestParam String code);

    @Operation(
            summary = "내 매칭 신청서로 매칭하기",
            description = """
                    [operation]
                    - 로그인 한 회원의 매칭 신청서로 비슷한 채팅방을 검색합니다.
                    
                    [condition]
                    - 정원 모집이 완료되지 않은 채팅방을 검색합니다.
                    - 내가 입장 했었던 채팅방은 검색에서 제외됩니다.
                    - 내 매칭 신청서의 여행 시작일 ~ 여행 종료일 사이에 여행을 시작하는 채팅방을 검색합니다.
                    - 내 매칭 신청서의 지역 대분류와 일치하는 채팅방을 검색합니다.
                      단, 지역 대분류가 전국이라면 지역 검색을 스킵합니다.
                    - 내 매칭 신청서의 성별 비율과 일치하는 채팅방을 검색합니다.
                    - 내 매칭 신청서의 여행 스타일과 한 개 이상 일치하는 채팅방을 검색합니다.

                    [validation]
                    - 존재하지 않는 회원의 매칭 신청서로는 매칭할 수 없습니다.
                    - 존재하지 않는 매칭 신청서로는 매칭할 수 없습니다.
                    - 이미 같은 매칭 신청서로 참여된 채팅방이 존재하면 새로운 채팅방을 매칭할 수 없습니다.
                    """
    )
    ResponseEntity<GetMatchApplicationFormResponse> getMatchedApplicationForm(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long matchingFormId
    );
}
