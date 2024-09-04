package com.meetravel.domain.matching_form.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAreaResponse {

    private List<Area> areaList;

    @Getter
    @Builder
    public static class Area {
        private String code;
        private String name;
        private List<DetailArea> detailAreaList;
    }

    @Getter
    @Builder
    public static class DetailArea {
        private String detailCode;
        private String detailName;
    }

}
