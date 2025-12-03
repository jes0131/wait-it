package com.jes.waitit.domain.submission.dto.request;

import com.jes.waitit.domain.submission.enums.SubmissionState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "제출 상태 변경 요청")
public class SubmissionUpdateRequestDTO {
    @Schema(description = "변경할 제출 상태", example = "CANCELED")
    private SubmissionState submissionState;

    @Schema(description = "CANCELED로 변경 시 거절 사유", example = "마감시간입니다.")
    private String comment;
}
