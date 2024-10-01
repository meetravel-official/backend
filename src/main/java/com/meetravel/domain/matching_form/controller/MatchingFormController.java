package com.meetravel.domain.matching_form.controller;

import com.meetravel.domain.matching_form.dto.request.CreateMatchingFormRequest;
import com.meetravel.domain.matching_form.dto.response.*;
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
    @GetMapping
    public ResponseEntity<GetMatchingFormResponse> getMatchingForm(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(matchingFormService.getMatchingForm(userDetails.getUsername()));
    }

    @Override
    @PostMapping
    public ResponseEntity<CreateMatchingFormResponse> createMatchingForm(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid CreateMatchingFormRequest request
    ) {
        CreateMatchingFormResponse response = matchingFormService.createMatchingForm(
                userDetails.getUsername(),
                request
        );

        return ResponseEntity.ok(response);
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

    @Override
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
