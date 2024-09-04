package com.meetravel.domain.tour_api;

import lombok.Getter;

import java.util.List;

@Getter
public class TourApiDetailAreaResponse {

    private Header header;
    private Body body;

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
        private List<Item> itemList;
    }

    @Getter
    public static class Item {
        private String code;
        private String name;
        private String rnum;
    }

}
