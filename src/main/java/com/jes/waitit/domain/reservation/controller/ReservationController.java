package com.jes.waitit.domain.reservation.controller;

import com.jes.waitit.domain.reservation.dto.ReservationCreateRequestDTO;
import com.jes.waitit.domain.reservation.dto.ReservationDetailResponseDTO;
import com.jes.waitit.domain.reservation.dto.ReservationSubmitRequestDTO;
import com.jes.waitit.domain.reservation.dto.ReservationSubmitResponseDTO;
import com.jes.waitit.domain.reservation.service.ReservationService;
import com.jes.waitit.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createReservation(
            Authentication authentication,
            @RequestBody ReservationCreateRequestDTO dto
    ) {
        reservationService.createReservation(authentication.getName(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("예약 폼 생성 완료"));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ReservationDetailResponseDTO>> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.getReservation(id)));
    }

    @PostMapping("{id}/submit")
    public ResponseEntity<ApiResponse<ReservationSubmitResponseDTO>> submitReservation(@PathVariable Long id, @RequestBody ReservationSubmitRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.submitReservation(id, dto)));
    }
}
