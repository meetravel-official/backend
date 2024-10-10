package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.enums.ChatRoomSort;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public class SearchChatRoomRequest {
    @Parameter(description = "검색 키워드")
    private final String query;
    @Parameter(description = "지역 대분류 코드")
    private final String areaCode;
    @Parameter(description = "정렬")
    private final ChatRoomSort sort;
    @Parameter(description = "페이지 번호")
    private final int page;
    @Parameter(description = "페이지 크기")
    private final int pageSize;

    public Pageable generatePageable() {
        return PageRequest.of(
                this.page,
                this.pageSize
        );
    }

    public SearchChatRoomRequest(
            String query,
            String areaCode,
            String sort,
            Integer page,
            Integer pageSize
    ) {
        this.query = (query == null || query.isEmpty()) ? null : query;
        this.areaCode = (areaCode == null || areaCode.isEmpty()) ? null : areaCode;
        this.sort = (sort == null || sort.isEmpty()) ? null : ChatRoomSort.valueOf(sort);
        this.page = (page == null || page <= 0) ? 0 : page;
        this.pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
    }
}
