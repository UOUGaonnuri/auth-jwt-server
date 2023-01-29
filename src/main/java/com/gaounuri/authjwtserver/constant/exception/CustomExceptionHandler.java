package com.gaounuri.authjwtserver.constant.exception;

import com.gaounuri.authjwtserver.constant.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<BaseResponse<String>> handleCustomException(CustomException e){
        log.error(e.errorCode.getMessage());
        return ResponseEntity.status(e.errorCode.getHttpStatus())
                .body(BaseResponse.fail(e.errorCode.getHttpStatus(), e.errorCode.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse<String>> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."));
    }
}
