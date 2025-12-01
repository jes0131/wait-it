package com.jes.waitit.domain.auth.service;

import static com.jes.waitit.global.security.TokenConstants.*;

import com.jes.waitit.domain.auth.dto.request.LoginRequestDTO;
import com.jes.waitit.domain.auth.dto.request.RefreshRequestDTO;
import com.jes.waitit.domain.auth.dto.request.RegisterRequestDTO;
import com.jes.waitit.domain.auth.dto.response.LoginResponseDTO;
import com.jes.waitit.domain.auth.dto.response.RefreshResponseDTO;
import com.jes.waitit.domain.auth.entity.RefreshToken;
import com.jes.waitit.domain.auth.repository.RefreshTokenRepository;
import com.jes.waitit.domain.user.entity.User;
import com.jes.waitit.domain.user.service.UserService;
import com.jes.waitit.global.exception.CustomException;
import com.jes.waitit.global.exception.ErrorCode;
import com.jes.waitit.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public void register(RegisterRequestDTO dto) {
        if (userService.existsByUsername(dto.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        }

        User user = User.builder()
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        userService.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        String accessToken = tokenProvider.createToken(dto.getUsername());
        String refreshToken = tokenProvider.createRefreshToken(dto.getUsername());

        User user = userService.findByUsername(dto.getUsername());

        RefreshToken rf = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .expiryDate(LocalDateTime.now().plus(REFRESH_TOKEN_VALIDITY, ChronoUnit.MILLIS))
                .build();
        refreshTokenRepository.save(rf);

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshResponseDTO refresh(RefreshRequestDTO dto) {
        if (!tokenProvider.validateToken(dto.getRefreshToken())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        RefreshToken existingToken = refreshTokenRepository.findByToken(dto.getRefreshToken())
                .orElseThrow(() -> new CustomException(ErrorCode.TOKEN_NOT_FOUND));

        if (existingToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        }

        String username = tokenProvider.getUsername(existingToken.getToken());
        String newAccessToken = tokenProvider.createToken(username);

        return RefreshResponseDTO.builder()
                .accessToken(newAccessToken)
                .build();
    }
}
