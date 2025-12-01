package com.jes.waitit.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RefreshRequestDTO {
    @NotBlank
    private String refreshToken;
}
