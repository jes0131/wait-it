package com.jes.waitit.domain.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "예약 폼 제출")
public class ReservationSubmitRequestDTO {
    List<QuestionSubmitRequestDTO> answers;
}
