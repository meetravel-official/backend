package com.meetravel.domain.matching_form.service;

import com.meetravel.domain.matching_form.dto.request.CreateMatchingFormRequest;
import com.meetravel.domain.matching_form.dto.response.GetAreaResponse;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.repository.MatchingFormRepository;
import com.meetravel.domain.tour_api.TourApiAreaFeginClient;
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
    private final TourApiAreaFeginClient tourApiAreaFeginClient;

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
                .build();

        // 매칭 신청서 저장
        matchingFormRepository.save(matchingForm);

    }

    public GetAreaResponse getAreaResponse() {
        int numOfRows = 17; // 충분한 수 넣어줌
        int pageNo = 1;
        String mobileOS = "ETC";
        String mobileApp = "meetravel";
        String _type = "json";

        TourApiAreaResponse tourApiAreaResponse = tourApiAreaFeginClient.getArea(serviceKey, numOfRows, pageNo, mobileOS, mobileApp, _type);

        List<GetAreaResponse.Area> areaList = new ArrayList<>();
        for (TourApiAreaResponse.Item item : tourApiAreaResponse.getBody().getItemList()) {
            TourApiDetailAreaResponse tourApiDetailAreaResponse = tourApiAreaFeginClient.getDetailArea(serviceKey, numOfRows, pageNo, mobileOS, mobileApp, _type, item.getCode());

            /** 대분류 지역마다 상세 지역 개수가 다르기 때문에 이전에요청해서 받아온 totalCount로 다시 한 번 요청해야함 */
            List<GetAreaResponse.DetailArea> detailAreaList = getDetailAreaList(tourApiDetailAreaResponse.getBody().getTotalCount(),pageNo,mobileOS, mobileApp, _type, item.getCode());

            GetAreaResponse.Area area = GetAreaResponse.Area.builder()
                    .code(item.getCode())
                    .name(item.getName())
                    .detailAreaList(detailAreaList)
                    .build();
            areaList.add(area);
        }

        return GetAreaResponse.builder().areaList(areaList).build();
    }

    private List<GetAreaResponse.DetailArea> getDetailAreaList(int numOfRows, int pageNo, String mobileOS, String mobileApp, String _type, String code) {
        TourApiDetailAreaResponse tourApiDetailAreaResponse = tourApiAreaFeginClient.getDetailArea(serviceKey, numOfRows, pageNo, mobileOS, mobileApp, _type, code);
        return tourApiDetailAreaResponse.getBody().getItemList().stream()
                .map(detailItem -> GetAreaResponse.DetailArea.builder()
                        .detailCode(detailItem.getCode())
                        .detailName(detailItem.getName())
                        .build())
                .toList();

    }


}
