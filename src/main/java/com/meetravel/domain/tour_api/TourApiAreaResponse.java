package com.meetravel.domain.tour_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TourApiAreaResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    public static class Response {
        @JsonProperty("header")
        private Header header;

        @JsonProperty("body")
        private Body body;
    }

    @Getter
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Getter
    public static class Body {
        @JsonProperty("numOfRows")
        private int numOfRows;

        @JsonProperty("pageNo")
        private int pageNo;

        @JsonProperty("totalCount")
        private int totalCount;

        @JsonProperty("items")
        private Items items;
    }

    @Getter
    public static class Items {
        @JsonProperty("item")
        private List<Item> itemList;
    }

    @Getter
    public static class Item {
        @JsonProperty("code")
        private String code;

        @JsonProperty("name")
        private String name;

        @JsonProperty("rnum")
        private String rnum;
    }
}
