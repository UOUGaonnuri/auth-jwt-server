package com.gaounuri.authjwtserver.user.dto;

import antlr.Token;
import com.gaounuri.authjwtserver.user.enums.Role;
import com.gaounuri.authjwtserver.user.model.TestUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class TestUserDTO {
    private TestUserDTO(){}
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TestUserInfo {
        private Long userId;
        private String userName;
        private String userEmail;
        private Role role;

        public static TestUserInfo entityToDTO(TestUser entity){
            return TestUserInfo.builder()
                    .userId(entity.getUserId())
                    .userName(entity.getUserName())
                    .userEmail(entity.getUserEmail())
                    .role(entity.getRole())
                    .build();
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RegisterReq {
        private String userEmail;
        private String userName;
        private String password;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RegisterRes {
        private String userEmail;
        private String userName;
    }
    @Data
    @AllArgsConstructor
    public static class TokenInfo {
        private String accessToken;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LoginReq {
        private String userEmail;
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication(){
            return new UsernamePasswordAuthenticationToken(this.userEmail, this.password);
        }
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LoginRes {
        private TestUserInfo userInfo;
        private TokenInfo tokenInfo;
    }
    @Data
    @AllArgsConstructor
    public static class logout {
        private String userName;
    }
}
