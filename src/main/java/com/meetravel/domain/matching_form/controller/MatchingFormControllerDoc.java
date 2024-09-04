package com.meetravel.domain.matching_form.controller;

import com.meetravel.domain.matching_form.dto.request.CreateMatchingFormRequest;
import com.meetravel.domain.matching_form.dto.response.GetAreaResponse;
import com.meetravel.domain.matching_form.dto.response.GetDetailAreaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "매칭 신청서 API")
public interface MatchingFormControllerDoc {

    @Operation(summary = "매칭 신청서 작성", description = "매칭 신청서를 작성합니다.")
    void createMatchingApplicationForm(@PathVariable String userId,
                                       @RequestBody @Valid CreateMatchingFormRequest request);

    @Operation(summary = "매칭 신청서에서 선택할 지역(대분류) 목록 조회", description = "매칭 신청서에서 선택할 지역(대분류) 목록을 조회합니다.")
    GetAreaResponse getAreaResponse();

    @Operation(summary = "매칭 신청서에서 선택할 지역(소분류) 목록 조회", description = "매칭 신청서에서 선택할 지역(소분류) 목록을 조회합니다.")
    GetDetailAreaResponse getDetailAreaResponse(@RequestParam String code);

}
