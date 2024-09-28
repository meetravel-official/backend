package com.meetravel.domain.chatroom.dto;

import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class GetChatMessageRequest {
    @Parameter(description = "마지막 채팅 메세지 번호")
    private final String lastChatMessageId;
    @Parameter(description = "페이지 번호")
    private final int page;
    @Parameter(description = "페이지 크기")
    private final int pageSize;

    public Pageable generatePageable() {
        return PageRequest.of(
                this.page,
                this.pageSize,
                Sort.by(Sort.Direction.DESC, "id")
        );
    }

    public GetChatMessageRequest(
            Integer page,
            Integer pageSize,
            String lastChatMessageId
    ) {
        if (lastChatMessageId != null && lastChatMessageId.isEmpty()) {
            throw new BadRequestException(ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION);
        }

        this.lastChatMessageId = lastChatMessageId;
        this.page = (page == null || page <= 0) ? 0 : page;
        this.pageSize = (pageSize == null || pageSize <= 0) ? 100 : pageSize;
    }
}
