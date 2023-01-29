package com.gaounuri.authjwtserver.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //BAD_REQUEST
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청이 올바르지 않습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "올바르지 않은 토큰 요청입니다."),

    //UNAUTHORIZED
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Token Expired"),

    //NOT_FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 유저의 정보를 찾을 수 없습니다."),

    //FORBIDDEN
    FORBIDDEN(HttpStatus.FORBIDDEN,  "금지된 요청입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.FORBIDDEN, "리프레쉬 토큰이 만료되었습니다."),

    //CONFLICT
    DUPLICATE_USER(HttpStatus.CONFLICT, "이미 해당 유저가 존재합니다."),

    //INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,  "내부 서버 오류입니다. 관리자에게 문의하세요");

    private final HttpStatus httpStatus;
    private final String message;
}
