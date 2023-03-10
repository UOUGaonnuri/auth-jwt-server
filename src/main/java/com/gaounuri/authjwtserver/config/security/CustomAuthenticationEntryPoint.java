package com.gaounuri.authjwtserver.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaounuri.authjwtserver.constant.dto.BaseResponse;
import com.gaounuri.authjwtserver.constant.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final BaseResponse<String> expiredExceptionResponse =
            BaseResponse.fail(ErrorCode.EXPIRED_TOKEN.getHttpStatus(), ErrorCode.EXPIRED_TOKEN.getMessage());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), expiredExceptionResponse);
        response.getOutputStream().flush();
    }
}
