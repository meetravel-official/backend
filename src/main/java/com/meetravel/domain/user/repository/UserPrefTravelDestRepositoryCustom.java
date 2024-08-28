package com.meetravel.domain.user.repository;

import com.meetravel.domain.travel_destination.enums.TravelDest;

import java.util.List;

public interface UserPrefTravelDestRepositoryCustom {

    List<TravelDest> getUserPrefTravelDestList(String userId);
}
