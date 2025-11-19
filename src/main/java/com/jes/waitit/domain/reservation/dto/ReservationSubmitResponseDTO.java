package com.jes.waitit.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationSubmitResponseDTO {
    private Integer waitingNum;
    private String password;
}
