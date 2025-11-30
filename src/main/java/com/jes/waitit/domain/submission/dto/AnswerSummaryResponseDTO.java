package com.jes.waitit.domain.submission.dto;

import lombok.Getter;

@Getter
public class AnswerSummaryResponseDTO {
    private final String content;

    public AnswerSummaryResponseDTO(String content) {
        this.content = content;
    }
}
