package com.jes.waitit.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Schema(description = "로그인 요청")
public class LoginRequestDTO {
    @Schema(description = "유저 아이디", example = "username")
    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 4, max = 20, message = "아이디는 4 ~ 20글자여야 합니다.")
    private String username;

    @Schema(description = "비밀번호", example = "password")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 6, max = 20, message = "비밀번호는 6 ~ 20글자여야 합니다.")
    private String password;
}
