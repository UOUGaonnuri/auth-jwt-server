package com.gaounuri.authjwtserver.constant.exception;

import com.gaounuri.authjwtserver.constant.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    ErrorCode errorCode;
}
