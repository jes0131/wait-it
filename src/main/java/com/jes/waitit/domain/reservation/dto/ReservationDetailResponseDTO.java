package com.jes.waitit.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReservationDetailResponseDTO {
    private String title;
    private String description;
    private String authorName;
    private LocalDateTime createdAt;
    private List<QuestionDetailResponseDTO> questions;
}
