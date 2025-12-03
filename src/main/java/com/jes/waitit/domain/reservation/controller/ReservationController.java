package com.jes.waitit.domain.reservation.controller;

import com.jes.waitit.domain.reservation.dto.request.ReservationCreateRequestDTO;
import com.jes.waitit.domain.reservation.dto.response.ReservationDetailResponseDTO;
import com.jes.waitit.domain.reservation.dto.request.ReservationSubmitRequestDTO;
import com.jes.waitit.domain.reservation.dto.response.ReservationSubmitResponseDTO;
import com.jes.waitit.domain.reservation.service.ReservationService;
import com.jes.waitit.domain.submission.dto.response.SubmissionSummaryResponseDTO;
import com.jes.waitit.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reservation")
@RequiredArgsConstructor
@Tag(name = "Reservation", description = "예약 관리 API")
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(
            summary = "예약 폼 생성",
            description = "예약 폼 정보와 질문들을 입력하여 예약 폼을 생성합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "예약 폼 생성 완료"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404", description = "존재하지 않는 유저",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "존재하지 않는 유저",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 404,
                                                "message": "존재하지 않는 유저입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createReservation(
            Authentication authentication,
            @RequestBody ReservationCreateRequestDTO dto
    ) {
        reservationService.createReservation(authentication.getName(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("예약 폼 생성 완료"));
    }

    @Operation(
            summary = "예약 폼 정보 조회",
            description = "예약 폼 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "요청 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404", description = "존재하지 않는 예약",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "존재하지 않는 예약",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 404,
                                                "message": "존재하지 않는 예약입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ReservationDetailResponseDTO>> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.getReservation(id)));
    }

    @Operation(
            summary = "제출 조회",
            description = "{id}에 해당하는 예약 폼에 대한 제출을 모두 불러옵니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "요청 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404", description = "존재하지 않는 예약",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "존재하지 않는 예약",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 404,
                                                "message": "존재하지 않는 예약입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403", description = "권한 부족",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "권한 부족",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 403,
                                                "message": "해당 예약의 제출 정보를 조회할 권한이 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("{id}/submission")
    public ResponseEntity<ApiResponse<Page<SubmissionSummaryResponseDTO>>> getReservationSubmission(
            @PathVariable Long id,
            Authentication authentication,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                reservationService.getSubmissions(id, authentication.getName(), pageable)
        ));
    }


    @Operation(
            summary = "예약 폼 삭제",
            description = "예약 폼을 삭제합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "204", description = "요청 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404", description = "존재하지 않는 예약",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "존재하지 않는 예약",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 404,
                                                "message": "존재하지 않는 예약입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403", description = "권한 부족",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "권한 부족",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 403,
                                                "message": "해당 예약을 삭제할 권한이 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, Authentication authentication) {
        reservationService.deletedReservation(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "예약 폼 제출",
            description = "예약 폼에 대한 응답을 제출합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "요청 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404", description = "존재하지 않는 예약",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "존재하지 않는 예약",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 404,
                                                "message": "존재하지 않는 예약입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("{id}/submit")
    public ResponseEntity<ApiResponse<ReservationSubmitResponseDTO>> submitReservation(@PathVariable Long id, @RequestBody ReservationSubmitRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(reservationService.submitReservation(id, dto)));
    }
}
