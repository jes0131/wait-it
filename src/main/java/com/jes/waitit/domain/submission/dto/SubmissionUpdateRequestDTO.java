package com.jes.waitit.domain.submission.dto;

import com.jes.waitit.domain.submission.enums.SubmissionState;
import lombok.Getter;

@Getter
public class SubmissionUpdateRequestDTO {
    private SubmissionState submissionState;
    private String comment;
}
