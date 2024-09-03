package com.meetravel.domain.user.dto.response;

import com.meetravel.domain.travel_destination.enums.TravelDest;
import com.meetravel.domain.user.enums.MBTI;
import com.meetravel.domain.user.enums.PlanningType;
import com.meetravel.domain.user.enums.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class GetMyPageResponse {
    private String profileImageUrl;
    private String nickname;
    private LocalDate birthDate;
    private PlanningType planningType;
    private String hobby;
    private MBTI mbti;
    private String intro;
    private List<Review> reviewList;
    private List<TravelDest> userPrefTravelDestList;

}
