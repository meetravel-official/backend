package com.meetravel.domain.chatroom.vo;

import com.meetravel.domain.matching_form.enums.Gender;
import com.meetravel.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChatRoomUserPreviewInfo {
    @Schema(description = "회원 ID")
    private final String userId;
    @Schema(description = "회원 닉네임")
    private final String nickname;
    @Schema(description = "회원 생년월일", format = "yyyy-MM-dd")
    private final String birthDate;
    @Schema(description = "회원 성별")
    private final Gender gender;
    @Schema(description = "프로필 이미지 URL")
    private final String profileImageUrl;

    public ChatRoomUserPreviewInfo(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.nickname = userEntity.getNickname();
        this.birthDate = userEntity.getBirthDate().toString();
        this.gender = userEntity.getGender();
        this.profileImageUrl = userEntity.getProfileImageUrl();
    }
}
