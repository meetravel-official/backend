package com.meetravel.domain.tour_api;

import lombok.Getter;

import java.util.List;

@Getter
public class TourApiDetailCommonResponse {
    private Response response;

    @Getter
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    public static class Body {
        private int numOfRows;
        private int pageNo;
        private int totalCount;
        private Items items;
    }

    @Getter
    public static class Items {
        private List<Item> item;
    }

    @Getter
    public static class Item {
        private String overview;
        private String contentid;
        private String sigungucode;
        private String cat1;
        private String cat2;
        private String cat3;
        private String addr1;
        private String addr2;
        private String zipcode;
        private String mapx;
        private String mapy;
        private String mlevel;
        private String cpyrhtDivCd;
        private String contenttypeid;
        private String booktour;
        private String createdtime;
        private String homepage;
        private String modifiedtime;
        private String tel;
        private String telname;
        private String title;
        private String firstimage;
        private String firstimage2;
        private String areacode;
    }
}

