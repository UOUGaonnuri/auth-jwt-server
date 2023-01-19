package com.gaounuri.authjwtserver.constant.exception;

import com.gaounuri.authjwtserver.constant.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<BaseResponse> handleCustomException(CustomException e){
        return ResponseEntity.status(e.errorCode.getHttpStatus())
                .body(BaseResponse.fail(e.errorCode.getHttpStatus(), e.errorCode.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
