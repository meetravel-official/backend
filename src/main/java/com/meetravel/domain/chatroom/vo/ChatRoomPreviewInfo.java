package com.meetravel.domain.chatroom.vo;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.entity.TravelKeywordEntity;
import com.meetravel.domain.matching_form.enums.TravelKeyword;
import com.meetravel.domain.matching_form.vo.Area;
import com.meetravel.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatRoomPreviewInfo {
    @Schema(description = "채팅방 고유 번호")
    private final Long chatRoomId;
    @Schema(description = "지역")
    private final Area area;
    @Schema(description = "채팅방 입장 인원 수")
    private final ChatRoomPerson persons;
    @Schema(description = "여행 스타일 키워드")
    private final List<TravelKeyword> travelKeywords;
    @Schema(description = "여행 기간")
    private final TravelPlanDate travelPlanDate;

    public ChatRoomPreviewInfo(
            ChatRoomEntity chatRoomEntity,
            MatchingFormEntity matchingFormEntity,
            List<UserEntity> userEntities
    ) {
        this.chatRoomId = chatRoomEntity.getId();
        this.area = new Area(matchingFormEntity);
        this.persons = new ChatRoomPerson(userEntities);
        this.travelKeywords = matchingFormEntity.getTravelKeywordList()
                .stream()
                .map(TravelKeywordEntity::getKeyword)
                .toList();
        this.travelPlanDate = new TravelPlanDate(matchingFormEntity);
    }
}
