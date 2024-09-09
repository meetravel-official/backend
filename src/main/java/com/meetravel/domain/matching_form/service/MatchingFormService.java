package com.meetravel.domain.matching_form.service;

import com.meetravel.domain.matching_form.dto.request.CreateMatchingFormRequest;
import com.meetravel.domain.matching_form.dto.response.GetAreaResponse;
import com.meetravel.domain.matching_form.dto.response.GetDetailAreaResponse;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.repository.MatchingFormRepository;
import com.meetravel.domain.tour_api.TourApiAreaFeignClient;
import com.meetravel.domain.tour_api.TourApiAreaResponse;
import com.meetravel.domain.tour_api.TourApiDetailAreaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MatchingFormService {

    @Value("${tour_api.service_key}")
    private String serviceKey;

    private final MatchingFormRepository matchingFormRepository;
    private final TourApiAreaFeignClient tourApiAreaFeginClient;

    /**
     * 매칭 신청서 작성
     *
     * @param request
     */
    @Transactional
    public void createMatchingForm(String userId, CreateMatchingFormRequest request) {
        MatchingFormEntity matchingForm = MatchingFormEntity.builder()
                .userId(userId)
                .duration(request.getDuration())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .groupSize(request.getGroupSize())
                .genderRatio(request.getGenderRatio())
                .cost(request.getCost())
                .areaCode(request.getArea().getCode())
                .areaName(request.getArea().getName())
                .detailAreaCode(request.getDetailArea().getDetailCode())
                .detailAreaName(request.getDetailArea().getDetailName())
                .build();

        // 매칭 신청서 저장
        matchingFormRepository.save(matchingForm);

    }

    public GetAreaResponse getArea() {
        int numOfRows = 17; // 충분한 수 넣어줌
        int pageNo = 1;
        String mobileOS = "ETC";
        String mobileApp = "meetravel";
        String _type = "json";

        TourApiAreaResponse tourApiAreaResponse = tourApiAreaFeginClient.getArea(numOfRows, pageNo, mobileOS, mobileApp, _type, serviceKey);

        List<GetAreaResponse.Area> areaList = tourApiAreaResponse.getResponse().getBody().getItems().getItemList().stream()
                .map(item -> GetAreaResponse.Area.builder()
                        .code(item.getCode())
                        .name(item.getName())
                        .build())
                .toList();

        return GetAreaResponse.builder().areaList(areaList).build();
    }

    public GetDetailAreaResponse getDetailArea(String code) {
        int numOfRows = 17; // 충분한 수 넣어줌
        int pageNo = 1;
        String mobileOS = "ETC";
        String mobileApp = "meetravel";
        String _type = "json";
        TourApiDetailAreaResponse tourApiDetailAreaResponse = tourApiAreaFeginClient.getDetailArea(numOfRows, pageNo, mobileOS, mobileApp, code, _type, serviceKey);

        List<GetDetailAreaResponse.DetailArea> detailAreaList = tourApiDetailAreaResponse.getResponse().getBody().getItems().getItemList().stream()
                .map(item -> GetDetailAreaResponse.DetailArea.builder()
                        .detailCode(item.getCode())
                        .detailName(item.getName())
                        .build())
                .toList();

        return GetDetailAreaResponse.builder().detailAreaList(detailAreaList).build();

    }

}
