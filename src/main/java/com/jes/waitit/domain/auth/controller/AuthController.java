package com.jes.waitit.domain.auth.controller;

import com.jes.waitit.domain.auth.dto.request.LoginRequestDTO;
import com.jes.waitit.domain.auth.dto.request.RefreshRequestDTO;
import com.jes.waitit.domain.auth.dto.request.RegisterRequestDTO;
import com.jes.waitit.domain.auth.dto.response.LoginResponseDTO;
import com.jes.waitit.domain.auth.dto.response.RefreshResponseDTO;
import com.jes.waitit.domain.auth.service.AuthService;
import com.jes.waitit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequestDTO dto) {
        authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입 완료"));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(dto)));
    }

    @PostMapping("refresh")
    public ResponseEntity<ApiResponse<RefreshResponseDTO>> refresh(@Valid @RequestBody RefreshRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(authService.refresh(dto)));
    }
}
