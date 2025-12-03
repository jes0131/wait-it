package com.jes.waitit.domain.auth.controller;

import com.jes.waitit.domain.auth.dto.request.LoginRequestDTO;
import com.jes.waitit.domain.auth.dto.request.RefreshRequestDTO;
import com.jes.waitit.domain.auth.dto.request.RegisterRequestDTO;
import com.jes.waitit.domain.auth.dto.response.LoginResponseDTO;
import com.jes.waitit.domain.auth.dto.response.RefreshResponseDTO;
import com.jes.waitit.domain.auth.service.AuthService;
import com.jes.waitit.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "인증 API")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "회원가입",
            description = "username, password, nickname을 입력하여 회원가입합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "회원가입 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409", description = "이미 가입된 아이디",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "아이디 중복",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 409,
                                                "message": "이미 가입된 아이디입니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequestDTO dto) {
        authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입 완료"));
    }

    @Operation(summary = "로그인", description = "username, password를 입력하여 로그인합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "로그인 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401", description = "올바르지 않은 아이디 또는 비밀번호",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "올바르지 않은 아이디 또는 비밀번호",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 401,
                                                "message": "아이디 또는 비밀번호가 올바르지 않습니니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(dto)));
    }

    @Operation(
            summary = "accessToken 재발급",
            description = "refreshToken을 입력하여 accessToken을 재발급합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "요청 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401", description = "올바르지 않은 토큰",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "올바르지 않은 토큰",
                                    value = """
                                            {
                                                "success": false,
                                                "status": 401,
                                                "message": "토큰이 올바르지 않습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("refresh")
    public ResponseEntity<ApiResponse<RefreshResponseDTO>> refresh(@Valid @RequestBody RefreshRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.success(authService.refresh(dto)));
    }
}
