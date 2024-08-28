package com.meetravel.domain.matching_form.dto.request;

import com.meetravel.domain.matching_form.enums.Cost;
import com.meetravel.domain.matching_form.enums.Duration;
import com.meetravel.domain.matching_form.enums.GenderRatio;
import com.meetravel.domain.matching_form.enums.GroupSize;
import com.meetravel.domain.travel_destination.enums.TravelDest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CreateMatchingFormRequest {

    private Duration duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private GroupSize groupSize;
    private GenderRatio genderRatio;
    private Cost cost;
    private TravelDest travelDest;


}
