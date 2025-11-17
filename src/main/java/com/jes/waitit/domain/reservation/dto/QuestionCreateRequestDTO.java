package com.jes.waitit.domain.reservation.dto;

import com.jes.waitit.domain.reservation.enums.QuestionType;
import lombok.Getter;

@Getter
public class QuestionCreateRequestDTO {
    private Integer order;
    private QuestionType questionType;
    private String title;
    private String description;
    private String placeholder;
    private boolean required;
}
