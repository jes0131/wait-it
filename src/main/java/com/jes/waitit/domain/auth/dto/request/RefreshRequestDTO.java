package com.jes.waitit.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "accessToken 발급 요청")
public class RefreshRequestDTO {
    @Schema(description = "refreshToken", example = "header.payload.signature")
    @NotBlank
    private String refreshToken;
}
