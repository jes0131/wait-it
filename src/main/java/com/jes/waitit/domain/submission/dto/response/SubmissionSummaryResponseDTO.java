package com.jes.waitit.domain.submission.dto.response;

import com.jes.waitit.domain.submission.enums.SubmissionState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(description = "예약에 대한 제출 조회 시 각 제출에 대한 정보")
public class SubmissionSummaryResponseDTO {
    @Schema(description = "제출 ID", example = "3")
    private Long id;

    @Schema(description = "제출 상태", example = "CANCELED", allowableValues = {"PENDING", "CANCELED", "RESOLVED"})
    private SubmissionState submissionState;

    @Schema(description = "취소 사유", example = "마감시간입니다.")
    private String comment;

    @Schema(description = "대기 번호", example = "13")
    private Integer waitingNum;

    private List<AnswerSummaryResponseDTO> answers;

    @Schema(description = "제출한 시간")
    private LocalDateTime submittedAt;
}
