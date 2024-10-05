package com.meetravel.domain.tour_api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tourApiAreaFeignClient", url = "http://apis.data.go.kr/B551011/KorService1")
public interface TourApiAreaFeignClient {

    @GetMapping(value = "/areaCode1", produces = "application/json")
    TourApiAreaResponse getArea(
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("MobileOS") String mobileOS,
            @RequestParam("MobileApp") String mobileApp,
            @RequestParam("_type") String type,
            @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping(value = "/areaCode1", produces = "application/json")
    TourApiDetailAreaResponse getDetailArea(
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("MobileOS") String mobileOS,
            @RequestParam("MobileApp") String mobileApp,
            @RequestParam("areaCode") String areaCode,
            @RequestParam("_type") String type,
            @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping(path = "/detailCommon1", produces = MediaType.APPLICATION_JSON_VALUE)
    TourApiDetailCommonResponse getDetailCommon(
            @RequestParam String MobileOS,
            @RequestParam String MobileApp,
            @RequestParam String _type,
            @RequestParam String contentId,
            @RequestParam String contentTypeId,
            @RequestParam String defaultYN,
            @RequestParam String firstImageYN,
            @RequestParam String areacodeYN,
            @RequestParam String catcodeYN,
            @RequestParam String addrinfoYN,
            @RequestParam String mapinfoYN,
            @RequestParam String overviewYN,
            @RequestParam Integer numOfRows,
            @RequestParam Integer pageNo,
            @RequestParam String serviceKey
    );
}
