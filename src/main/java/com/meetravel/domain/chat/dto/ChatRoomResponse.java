package com.meetravel.domain.chat.dto;

import com.meetravel.domain.chat.entity.ChatRoom;
import com.meetravel.domain.matching_form.enums.Gender;
import com.meetravel.domain.user.entity.UserEntity;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public record ChatRoomResponse(
    Long id,
    String title,
    String travelLocation,
    String travelType,  // 기획서 상에서 "액티비티", 여행 타입으로 생각
    LocalDate startDate,
    LocalDate endDate,
    Long femaleCount,
    Long maleCount,
    Long totalGenderCount
) {
    public static ChatRoomResponse fromChatRoom(ChatRoom chatRoom) {
        Map<Gender, Long> genderCounts = chatRoom.getUserList().stream()
            .collect(Collectors.groupingBy(UserEntity::getGender, Collectors.counting()));

        Long femaleCount = genderCounts.getOrDefault(Gender.FEMALE, 0L);
        Long maleCount = genderCounts.getOrDefault(Gender.MALE, 0L);

        return ChatRoomResponse.builder()
            .id(chatRoom.getId())
            .title(chatRoom.getTitle())
            .travelLocation("TEST_ACTIVITY")    // TODO: 기획 보고 바꾸기
            .startDate(chatRoom.getMatchingFormEntity().getStartDate())
            .endDate(chatRoom.getMatchingFormEntity().getEndDate())
            .femaleCount(femaleCount)
            .maleCount(maleCount)
            .totalGenderCount(femaleCount + maleCount)
            .build();
    }
}
