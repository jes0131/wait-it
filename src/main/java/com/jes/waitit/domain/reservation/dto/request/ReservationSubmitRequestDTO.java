package com.jes.waitit.domain.reservation.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ReservationSubmitRequestDTO {
    List<QuestionSubmitRequestDTO> answers;
}
