package com.jes.waitit.domain.reservation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "예약 폼 제출 응답")
public class ReservationSubmitResponseDTO {
    @Schema(description = "대기 번호", example = "13")
    private Integer waitingNum;

    @Schema(description = "제출 정보 조회를 위한 코드", example = "abcdef")
    private String accessCode;
}
