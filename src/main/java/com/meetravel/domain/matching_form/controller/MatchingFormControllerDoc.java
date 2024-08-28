package com.meetravel.domain.matching_form.controller;

import com.meetravel.domain.matching_form.dto.request.CreateMatchingFormRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "매칭 신청서 API")
public interface MatchingFormControllerDoc {

    @Operation(summary = "매칭 신청서 작성", description = "매칭 신청서를 작성합니다.")
    void createMatchingApplicationForm(@PathVariable String userId,
                                       @RequestBody @Valid CreateMatchingFormRequest request);

}
