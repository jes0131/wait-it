package com.jes.waitit.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProfileResponseDTO {
    private String nickname;
    private LocalDateTime createdAt;
}
