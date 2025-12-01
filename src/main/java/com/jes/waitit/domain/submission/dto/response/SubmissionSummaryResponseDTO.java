package com.jes.waitit.domain.submission.dto.response;

import com.jes.waitit.domain.submission.enums.SubmissionState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SubmissionSummaryResponseDTO {
    private Long id;
    private SubmissionState submissionState;
    private String comment;
    private Integer waitingNum;
    private List<AnswerSummaryResponseDTO> answers;
    private LocalDateTime submittedAt;
}
