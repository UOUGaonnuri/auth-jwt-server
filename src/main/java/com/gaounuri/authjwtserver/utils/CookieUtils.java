package com.gaounuri.authjwtserver.utils;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieUtils {

    public ResponseCookie createCookie(String cookieName, String value) {
        return ResponseCookie.from(cookieName, value)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .maxAge(JwtUtils.REFRESH_TOKEN_VALID_TIME / 1000)
                .build();
    }

    public Cookie getCookie(HttpServletRequest request, String cookieName){
        if(request.getCookies().length == 0){
            return null;
        } else {
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    return cookie;
                }
            }
        }
        return null;
    }

    public boolean deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName){
        Cookie cookie = getCookie(request, cookieName);
        if(cookie != null){
            Cookie myCookie = new Cookie(cookieName, null);
            myCookie.setMaxAge(0);
            myCookie.setPath("/");
            response.addCookie(myCookie);
            return true;
        } else {
            return false;
        }
    }
}
