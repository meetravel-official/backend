package com.meetravel.domain.tour_api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "korServiceClient", url = "http://apis.data.go.kr/B551011/KorService")
public interface TourApiAreaFeginClient {

    @GetMapping(value = "/areaCode", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    TourApiAreaResponse getArea(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("MobileOS") String mobileOS,
            @RequestParam("MobileApp") String mobileApp,
            @RequestParam("_type") String type
    );

    TourApiDetailAreaResponse getDetailArea(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("MobileOS") String mobileOS,
            @RequestParam("MobileApp") String mobileApp,
            @RequestParam("_type") String type,
            @RequestParam(value = "areaCode") String areaCode
    );
}
