package com.gaounuri.authjwtserver.user.service;

import com.gaounuri.authjwtserver.constant.enums.ErrorCode;
import com.gaounuri.authjwtserver.constant.exception.CustomException;
import com.gaounuri.authjwtserver.user.dto.TestUserDTO;
import com.gaounuri.authjwtserver.user.enums.Role;
import com.gaounuri.authjwtserver.user.model.TestUser;
import com.gaounuri.authjwtserver.user.model.repository.TestUserRepository;
import com.gaounuri.authjwtserver.user.service.inter.TestUserService;
import com.gaounuri.authjwtserver.utils.JwtUtils;
import com.gaounuri.authjwtserver.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestUserServiceImpl implements TestUserService {

    private final PasswordEncoder passwordEncoder;
    private final TestUserRepository testUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    public TestUserDTO.RegisterRes createUser(TestUserDTO.RegisterReq request) {
        TestUser user = TestUser.builder()
                .userEmail(request.getUserEmail())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        if(testUserRepository.existsTestUserByUserNameOrUserEmail(user.getUserName(), user.getUserEmail()).equals(Boolean.TRUE)){
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        }

        testUserRepository.save(user);

        return TestUserDTO.RegisterRes.builder()
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .build();
    }

    @Override
    public TestUserDTO.LoginRes login(TestUserDTO.LoginReq request) {
        TestUser user = testUserRepository.findTestUserByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String accessToken = jwtUtils.createToken(user.getUserEmail(), JwtUtils.ACCESS_TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:"+user.getUserEmail());

        if(refreshToken == null) {
            refreshToken = jwtUtils.createToken(user.getUserEmail(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
            redisUtils.setDataExpire("RT:" + user.getUserEmail(), refreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME);
        }

        return TestUserDTO.LoginRes.builder()
                .userInfo(TestUserDTO.TestUserInfo.entityToDTO(user))
                .tokenInfo(new TestUserDTO.TokenInfo(accessToken))
                .build();
    }

    @Override
    public TestUserDTO.TokenInfo reissue(String refreshToken) {
        if(!jwtUtils.validateToken(refreshToken)){
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        String userEmail = jwtUtils.getUserEmail(refreshToken);
        String savedRefreshToken = redisUtils.getData("RT:"+userEmail);
        if(refreshToken.isEmpty() || !refreshToken.equals(savedRefreshToken)){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } else {
            String newAccessToken = jwtUtils.createToken(userEmail, JwtUtils.ACCESS_TOKEN_VALID_TIME);
            return new TestUserDTO.TokenInfo(newAccessToken);
        }
    }

    @Override
    public TestUserDTO.logout logout(String accessToken) {
        if(!jwtUtils.validateToken(accessToken)){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        Authentication authentication = jwtUtils.getAuthentication(accessToken);

        if(redisUtils.getData("RT:"+authentication.getName()) != null){
            redisUtils.deleteData("RT:"+authentication.getName());
        }

        Long expiration = jwtUtils.getExpiration(accessToken);
        redisUtils.setDataExpire(accessToken, "logout", expiration);

        return new TestUserDTO.logout(authentication.getName());
    }
}
