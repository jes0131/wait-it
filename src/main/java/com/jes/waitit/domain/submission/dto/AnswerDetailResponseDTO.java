package com.jes.waitit.domain.submission.dto;

import com.jes.waitit.domain.reservation.enums.QuestionType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerDetailResponseDTO {
    private String title;
    private QuestionType questionType;
    private String content;
}
