package com.gaounuri.authjwtserver.user.controller;

import com.gaounuri.authjwtserver.constant.dto.BaseResponse;
import com.gaounuri.authjwtserver.constant.enums.ErrorCode;
import com.gaounuri.authjwtserver.constant.exception.CustomException;
import com.gaounuri.authjwtserver.user.dto.TestUserDTO;
import com.gaounuri.authjwtserver.user.service.TestUserServiceImpl;
import com.gaounuri.authjwtserver.utils.CookieUtils;
import com.gaounuri.authjwtserver.utils.JwtUtils;
import com.gaounuri.authjwtserver.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1")
public class AuthController {

    private final TestUserServiceImpl testUserService;
    private final JwtUtils jwtUtils;

    private final RedisUtils redisUtils;
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<TestUserDTO.RegisterRes>> register(@RequestBody TestUserDTO.RegisterReq request){
        return ResponseEntity.ok().body(BaseResponse.success(testUserService.createUser(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TestUserDTO.LoginRes>> login(@RequestBody TestUserDTO.LoginReq request){
        TestUserDTO.LoginRes result = testUserService.login(request);
        String refreshToken = redisUtils.getData("RT:"+request.getUserEmail());
        ResponseCookie refreshTokenCookie = CookieUtils.createCookie(JwtUtils.REFRESH_TOKEN_NAME, refreshToken);
        return ResponseEntity.ok().header(SET_COOKIE, refreshTokenCookie.toString()).body(BaseResponse.success(result));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<TestUserDTO.logout>> logout(@CookieValue(value = JwtUtils.REFRESH_TOKEN_NAME, defaultValue = "") String refreshToken,
                                                                   HttpServletRequest request, HttpServletResponse response){
        String accessToken = jwtUtils.resolveToken(request);
        TestUserDTO.logout result = testUserService.logout(accessToken);
        if(!refreshToken.isEmpty()){
            CookieUtils.deleteCookie(request, response, JwtUtils.REFRESH_TOKEN_NAME);
        }
        return ResponseEntity.ok().body(BaseResponse.success(result));
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<TestUserDTO.TokenInfo>> reissue(@CookieValue(value = JwtUtils.REFRESH_TOKEN_NAME, defaultValue = "") String refreshToken){
        if(!refreshToken.isEmpty()){
            TestUserDTO.TokenInfo result = testUserService.reissue(refreshToken);
            return ResponseEntity.ok(BaseResponse.success(result));
        } else {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }

}
