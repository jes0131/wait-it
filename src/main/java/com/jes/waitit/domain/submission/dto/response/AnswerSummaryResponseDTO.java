package com.jes.waitit.domain.submission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "예약에 대한 제출 조회 시 각 제출의 응답에 대한 정보")
public class AnswerSummaryResponseDTO {
    @Schema(description = "응답한 내용", example = "간장 마늘 치킨")
    private final String content;

    public AnswerSummaryResponseDTO(String content) {
        this.content = content;
    }
}
