package com.jes.waitit.domain.submission.controller;

import com.jes.waitit.domain.submission.dto.response.SubmissionDetailResponseDTO;
import com.jes.waitit.domain.submission.dto.request.SubmissionUpdateRequestDTO;
import com.jes.waitit.domain.submission.dto.response.SubmissionUpdateResponseDTO;
import com.jes.waitit.domain.submission.service.SubmissionService;
import com.jes.waitit.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/submission")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @GetMapping
    public ResponseEntity<ApiResponse<SubmissionDetailResponseDTO>> getSubmissionDetail(
            @RequestParam(name = "code") String accessCode
    ) {
        return ResponseEntity.ok(ApiResponse.success(submissionService.getSubmissionDetail(accessCode)));
    }

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
