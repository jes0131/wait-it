package com.jes.waitit.domain.submission.dto;

import com.jes.waitit.domain.submission.enums.SubmissionState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmissionUpdateResponseDTO {
    private Long id;
    private SubmissionState submissionState;
    private String comment;
}
