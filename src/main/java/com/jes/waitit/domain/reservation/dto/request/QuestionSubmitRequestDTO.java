package com.jes.waitit.domain.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "예약 폼 제출 시 포함되는 개별 질문 응답 정보")
public class QuestionSubmitRequestDTO {
    @Schema(description = "질문 순서", example = "1")
    private Integer order;

    @Schema(description = "질문 응답", example = "마늘 간장 치킨")
    private String content;
}
