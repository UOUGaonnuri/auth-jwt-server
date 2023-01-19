package com.gaounuri.authjwtserver.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 유저의 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
