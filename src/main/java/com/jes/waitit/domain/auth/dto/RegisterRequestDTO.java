package com.jes.waitit.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class RegisterRequestDTO {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 4, max = 20, message = "아이디는 4 ~ 20글자여야 합니다.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Length(min = 4, max = 50, message = "닉네임은 4 ~ 50글자여야 합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 6, max = 20, message = "비밀번호는 6 ~ 20글자여야 합니다.")
    private String password;
}
