package com.meetravel.domain.matching_form.controller;

import com.meetravel.domain.matching_form.dto.request.CreateMatchingFormRequest;
import com.meetravel.domain.matching_form.dto.response.GetAreaResponse;
import com.meetravel.domain.matching_form.dto.response.GetDetailAreaResponse;
import com.meetravel.domain.matching_form.dto.response.GetMatchApplicationFormResponse;
import com.meetravel.domain.matching_form.service.MatchingFormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/matching-form")
public class MatchingFormController implements MatchingFormControllerDoc {

    private final MatchingFormService matchingFormService;

    @Override
    @PostMapping("/{userId}")
    public void createMatchingApplicationForm(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody @Valid CreateMatchingFormRequest request) {
        matchingFormService.createMatchingForm(userDetails.getUsername(), request);
    }

    @Override
    @GetMapping("/areas")
    public GetAreaResponse getAreaResponse() {
        return matchingFormService.getArea();
    }

    @Override
    @GetMapping("/areas/detail")
    public GetDetailAreaResponse getDetailAreaResponse(@RequestParam String code) {
        return matchingFormService.getDetailArea(code);
    }

    @GetMapping("/match/{matchingFormId}")
    public ResponseEntity<GetMatchApplicationFormResponse> getMatchedApplicationForm(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long matchingFormId
    ) {
        GetMatchApplicationFormResponse response = matchingFormService.getMatchedApplicationForm(
                userDetails.getUsername(),
                matchingFormId
        );

        return ResponseEntity.ok(response);
    }

}
