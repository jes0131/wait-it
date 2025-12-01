package com.jes.waitit.domain.submission.dto.response;

import com.jes.waitit.domain.submission.enums.SubmissionState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SubmissionDetailResponseDTO {
    private Long id;
    private Integer waitingNum;
    private SubmissionState submissionState;
    private String comment;
    private List<AnswerDetailResponseDTO> answers;
    private LocalDateTime submittedAt;
}
