package com.jes.waitit.domain.submission.dto.response;

import com.jes.waitit.domain.submission.enums.SubmissionState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(description = "제출 상세 정보 조회")
public class SubmissionDetailResponseDTO {
    @Schema(description = "제출 ID", example = "3")
    private Long id;

    @Schema(description = "대기 번호", example = "13")
    private Integer waitingNum;

    @Schema(description = "제출 상태", example = "CANCELED", allowableValues = {"PENDING", "CANCELED", "RESOLVED"})
    private SubmissionState submissionState;

    @Schema(description = "취소 사유", example = "마감시간입니다.")
    private String comment;

    private List<AnswerDetailResponseDTO> answers;

    @Schema(description = "제출한 시간")
    private LocalDateTime submittedAt;
}
