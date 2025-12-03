package com.jes.waitit.domain.submission.dto.response;

import com.jes.waitit.domain.reservation.enums.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "제출 상세 정보 조회 시 포함되는 응답 상세 정보")
public class AnswerDetailResponseDTO {
    @Schema(description = "질문 제목", example = "메뉴")
    private String title;

    @Schema(description = "질문 종류", example = "TEXT", allowableValues = {"TEXT"})
    private QuestionType questionType;

    @Schema(description = "응답 내용", example = "간장 마늘 치킨")
    private String content;
}
