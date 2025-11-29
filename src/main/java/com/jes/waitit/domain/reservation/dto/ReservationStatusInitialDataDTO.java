package com.jes.waitit.domain.reservation.dto;

import lombok.Getter;

@Getter
public class ReservationStatusInitialDataDTO {
    private Integer waitingCount;
    private Integer lastProcessedWaitingNum;

    public ReservationStatusInitialDataDTO(Integer waitingCount, Integer lastProcessedWaitingNum) {
        this.waitingCount = waitingCount;
        this.lastProcessedWaitingNum = lastProcessedWaitingNum;
    }
}
