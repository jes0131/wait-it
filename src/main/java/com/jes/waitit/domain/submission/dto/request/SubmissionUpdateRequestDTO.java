package com.jes.waitit.domain.submission.dto.request;

import com.jes.waitit.domain.submission.enums.SubmissionState;
import lombok.Getter;

@Getter
public class SubmissionUpdateRequestDTO {
    private SubmissionState submissionState;
    private String comment;
}
