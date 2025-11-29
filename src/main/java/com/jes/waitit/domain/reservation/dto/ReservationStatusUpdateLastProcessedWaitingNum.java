package com.jes.waitit.domain.reservation.dto;

import lombok.Getter;

@Getter
public class ReservationStatusUpdateLastProcessedWaitingNum {
    private Integer lastProcessedWaitingNum;

    public ReservationStatusUpdateLastProcessedWaitingNum(Integer lastProcessedWaitingNum) {
        this.lastProcessedWaitingNum = lastProcessedWaitingNum;
    }
}