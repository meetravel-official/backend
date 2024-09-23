package com.meetravel.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateNicknameRequest {

    @NotBlank
    @Size(min = 2, max = 6, message = "길이는 2자 이상 6자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "한글 또는 영문만 입력 가능합니다.")
    private String nickname;
}
