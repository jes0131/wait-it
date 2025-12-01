package com.jes.waitit.domain.reservation.websocket.dto;

import lombok.Getter;

@Getter
public class ReservationStatusUpdateLastProcessedWaitingNumDTO {
    private Integer lastProcessedWaitingNum;

    public ReservationStatusUpdateLastProcessedWaitingNumDTO(Integer lastProcessedWaitingNum) {
        this.lastProcessedWaitingNum = lastProcessedWaitingNum;
    }
}