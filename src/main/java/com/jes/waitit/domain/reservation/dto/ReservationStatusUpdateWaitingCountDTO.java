package com.jes.waitit.domain.reservation.dto;

import lombok.Getter;

@Getter
public class ReservationStatusUpdateWaitingCountDTO {
    private Integer waitingCount;

    public ReservationStatusUpdateWaitingCountDTO(Integer waitingCount) {
        this.waitingCount = waitingCount;
    }
}