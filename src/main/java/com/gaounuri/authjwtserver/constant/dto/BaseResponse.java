package com.gaounuri.authjwtserver.constant.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor(staticName = "of")
public class BaseResponse<T> {
    private final HttpStatus status;
    private final T data;
    private final String message;

    public static <T> BaseResponse<T> success(T resultData) {
        return new BaseResponse<>(HttpStatus.OK, resultData, "SUCCESS");
    }

    public static BaseResponse<String> fail(HttpStatus status, String errorMsg){
        return new BaseResponse<>(status, errorMsg, "FAIL");
    }
}
