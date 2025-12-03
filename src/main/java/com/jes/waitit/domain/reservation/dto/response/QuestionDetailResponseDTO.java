package com.jes.waitit.domain.reservation.dto.response;

import com.jes.waitit.domain.reservation.enums.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "예약 폼 정보 조회 시 포함되는 개별 질문 상세 정보")
public class QuestionDetailResponseDTO {
    @Schema(description = "질문 순서", example = "1")
    private Integer order;

    @Schema(description = "질문 종류", example = "TEXT", allowableValues = {"TEXT"})
    private QuestionType questionType;

    @Schema(description = "질문 제목", example = "메뉴")
    private String title;

    @Schema(description = "질문 설명", example = "예약하실 메뉴를 입력해주세요")
    private String description;

    @Schema(description = "질문 input placeholder", example = "ex) 양념치킨")
    private String placeholder;

    @Schema(description = "질문 응답 필수 여부", example = "false")
    private boolean required;
}
