package com.jes.waitit.domain.reservation.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ReservationCreateRequestDTO {
    private String title;
    private String description;
    private List<QuestionCreateRequestDTO> questions;
}
