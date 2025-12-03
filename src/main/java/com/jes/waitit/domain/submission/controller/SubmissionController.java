package com.jes.waitit.domain.submission.controller;

import com.jes.waitit.domain.submission.dto.response.SubmissionDetailResponseDTO;
import com.jes.waitit.domain.submission.dto.request.SubmissionUpdateRequestDTO;
import com.jes.waitit.domain.submission.dto.response.SubmissionUpdateResponseDTO;
import com.jes.waitit.domain.submission.service.SubmissionService;
import com.jes.waitit.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/submission")
@RequiredArgsConstructor
@Tag(name = "submission", description = "제출 관리 API")
public class SubmissionController {
    private final SubmissionService submissionService;

    @Operation(
            summary = "제출 상세 정보 조회",
            description = "accessCode를 이용해 자신의 제출에 대한 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "요청 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404", description = "존재하지 않는 제출",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "존재하지 않는 제출",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 404,
                                                "message": "존재하지 않는 제출입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<SubmissionDetailResponseDTO>> getSubmissionDetail(
            @RequestParam(name = "code") String accessCode
    ) {
        return ResponseEntity.ok(ApiResponse.success(submissionService.getSubmissionDetail(accessCode)));
    }

    @Operation(
            summary = "제출 상태 변경",
            description = "제출에 상태를 변경합니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "요청 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404", description = "존재하지 않는 제출",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "존재하지 않는 제출",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 404,
                                                "message": "존재하지 않는 제출입니다.",
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
                                                "message": "해당 제출을 수정할 권한이 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping("{id}")
    public ResponseEntity<ApiResponse<SubmissionUpdateResponseDTO>> updateSubmission(
            @PathVariable Long id,
            Authentication authentication,
            @RequestBody SubmissionUpdateRequestDTO submissionUpdateRequestDTO
    ) {
        SubmissionUpdateResponseDTO response = submissionService.updateSubmission(id, authentication.getName(), submissionUpdateRequestDTO);
        submissionService.broadcastSubmission(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
