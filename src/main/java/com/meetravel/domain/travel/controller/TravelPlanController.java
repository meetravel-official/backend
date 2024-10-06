package com.meetravel.domain.travel.controller;

import com.meetravel.domain.travel.dto.GetChatRoomTravelPlanResponse;
import com.meetravel.domain.travel.dto.ModifyTravelPlanKeywordRequest;
import com.meetravel.domain.travel.service.TravelPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travels")
public class TravelPlanController implements TravelPlanControllerDoc {
    private final TravelPlanService travelPlanService;

    @PutMapping("/plans/keywords/chat-rooms/{chatRoomId}")
    public void modifyTravelPlanKeywords(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long chatRoomId,
            @RequestBody ModifyTravelPlanKeywordRequest request
    ) {
        travelPlanService.modifyTravelPlanKeywords(
                userDetails.getUsername(),
                chatRoomId,
                request
        );
    }

    @Override
    @GetMapping("/plans/chat-rooms/{chatRoomId}")
    public ResponseEntity<GetChatRoomTravelPlanResponse> getTravelPlanWithChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long chatRoomId
    ) {
        GetChatRoomTravelPlanResponse response = travelPlanService.getTravelPlanWithChatRoom(
                userDetails.getUsername(),
                chatRoomId
        );

        return ResponseEntity.ok(response);
    }
}
