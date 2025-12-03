package com.jes.waitit.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "accessToken 재발급 요청 성공 응답")
public class RefreshResponseDTO {
    @Schema(description = "accessToken", example = "header.payload.signature")
    private String accessToken;
}
