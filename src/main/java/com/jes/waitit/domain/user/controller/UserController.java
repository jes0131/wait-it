package com.jes.waitit.domain.user.controller;

import com.jes.waitit.domain.user.dto.ProfileResponseDTO;
import com.jes.waitit.domain.user.service.UserService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "프로필 조회", description = "유저의 프로필을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "요청 성공"
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
    @GetMapping("me")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> getProfile(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile(authentication.getName())));
    }
}
