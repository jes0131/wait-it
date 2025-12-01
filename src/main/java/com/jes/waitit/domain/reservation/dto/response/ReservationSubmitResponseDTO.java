package com.jes.waitit.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationSubmitResponseDTO {
    private Integer waitingNum;
    private String accessCode;
}
