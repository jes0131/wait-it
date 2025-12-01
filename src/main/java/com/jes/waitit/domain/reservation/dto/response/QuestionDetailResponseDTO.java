package com.jes.waitit.domain.reservation.dto.response;

import com.jes.waitit.domain.reservation.enums.QuestionType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionDetailResponseDTO {
    private Integer order;
    private QuestionType questionType;
    private String title;
    private String description;
    private String placeholder;
    private boolean required;
}
