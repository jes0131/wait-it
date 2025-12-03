package com.jes.waitit.domain.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "예약 폼 생성")
public class ReservationCreateRequestDTO {
    @Schema(description = "예약 폼 제목", example = "나리치킨")
    private String title;

    @Schema(description = "예약 폼 설명", example = "나리치킨 예약")
    private String description;

    private List<QuestionCreateRequestDTO> questions;
}
