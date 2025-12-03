package com.jes.waitit.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    DUPLICATE_USER(HttpStatus.CONFLICT, "이미 가입된 아이디입니다."),

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),

    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다."),
    RESERVATION_DELETE_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 예약을 삭제할 권한이 없습니다."),
    RESERVATION_GET_SUBMISSIONS_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 예약의 제출 정보를 조회할 권한이 없습니다."),

    SUBMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 제출입니다."),
    SUBMISSION_UPDATE_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 제출을 수정할 권한이 없습니다.");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage){
        this.status = status;
        this.defaultMessage = defaultMessage;
    }
}
