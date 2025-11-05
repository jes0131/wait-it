package com.jes.waitit.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshResponseDTO {
    private String accessToken;
}
