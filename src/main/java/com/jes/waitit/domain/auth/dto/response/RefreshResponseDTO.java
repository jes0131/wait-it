package com.jes.waitit.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshResponseDTO {
    private String accessToken;
}
