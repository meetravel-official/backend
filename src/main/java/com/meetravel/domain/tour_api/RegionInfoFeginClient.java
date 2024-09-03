package com.meetravel.domain.tour_api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "korServiceClient", url = "http://apis.data.go.kr/B551011/KorService")
public interface RegionInfoFeginClient {

    @GetMapping("/areaCode")
    String getAreaCode(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("MobileOS") String mobileOS,
            @RequestParam("MobileApp") String mobileApp,
            @RequestParam("_type") String type
    );
}
