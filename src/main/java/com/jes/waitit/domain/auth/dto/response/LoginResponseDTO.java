package com.jes.waitit.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "로그인 성공 응답")
public class LoginResponseDTO {
    @Schema(description = "accessToken", example = "header.payload.signature")
    private String accessToken;
    @Schema(description = "refreshToken", example = "header.payload.signature")
    private String refreshToken;
}
