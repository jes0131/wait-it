package com.jes.waitit.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "프로필 조회 응답")
public class ProfileResponseDTO {
    @Schema(description = "닉네임", example = "nickname")
    private String nickname;

    @Schema(description = "계정 생성일")
    private LocalDateTime createdAt;
}
