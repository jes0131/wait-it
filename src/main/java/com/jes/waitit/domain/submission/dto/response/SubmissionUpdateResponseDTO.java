package com.jes.waitit.domain.submission.dto.response;

import com.jes.waitit.domain.submission.enums.SubmissionState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "제출 상태 변경 요청 결과")
public class SubmissionUpdateResponseDTO {
    @Schema(description = "제출 ID", example = "3")
    private Long id;

    @Schema(description = "제출 상태", example = "CANCELED", allowableValues = {"PENDING", "CANCELED", "RESOLVED"})
    private SubmissionState submissionState;

    @Schema(description = "취소 사유", example = "마감시간입니다.")
    private String comment;
}
