package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.vo.ChatRoomPerson;
import com.meetravel.domain.chatroom.vo.ChatRoomUserPreviewInfo;
import com.meetravel.domain.chatroom.vo.TravelPlanDate;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.entity.TravelKeywordEntity;
import com.meetravel.domain.matching_form.enums.TravelKeyword;
import com.meetravel.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetChatRoomResponse {
    @Schema(description = "채팅방 입장 인원 정보")
    private final List<ChatRoomUserPreviewInfo> joinedUsers;
    @Schema(description = "채팅방 입장 인원 수")
    private final ChatRoomPerson joinedPersons;
    @Schema(description = "여행 스타일 키워드")
    private final List<TravelKeyword> travelKeywords;
    @Schema(description = "여행 기간")
    private final TravelPlanDate travelPlanDate;

    public GetChatRoomResponse(UserEntity botUserEntity, List<UserEntity> userEntities, MatchingFormEntity matchingFormEntity) {
        List<UserEntity> tempUserEntities = new ArrayList<>();
        if (botUserEntity != null) {
            tempUserEntities.add(botUserEntity);
        }
        tempUserEntities.addAll(userEntities);

        this.joinedUsers = tempUserEntities.stream()
                .map(ChatRoomUserPreviewInfo::new)
                .toList();
        this.joinedPersons = new ChatRoomPerson(userEntities);
        this.travelKeywords = matchingFormEntity.getTravelKeywordList()
                .stream()
                .map(TravelKeywordEntity::getKeyword)
                .toList();
        this.travelPlanDate = new TravelPlanDate(matchingFormEntity);
    }
}
