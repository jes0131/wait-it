package com.jes.waitit.domain.reservation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(description = "예약 폼 정보")
public class ReservationDetailResponseDTO {
    @Schema(description = "예약 폼 제목", example = "나리치킨")
    private String title;

    @Schema(description = "예약 폼 설명", example = "나리치킨 예약")
    private String description;

    @Schema(description = "예약 폼 작성자 닉네임", example = "나리치킨사장")
    private String authorName;

    @Schema(description = "예약 폼 생성일")
    private LocalDateTime createdAt;

    private List<QuestionDetailResponseDTO> questions;
}
