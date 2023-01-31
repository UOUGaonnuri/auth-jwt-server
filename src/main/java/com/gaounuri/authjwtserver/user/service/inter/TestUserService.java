package com.gaounuri.authjwtserver.user.service.inter;

import com.gaounuri.authjwtserver.user.dto.TestUserDTO;

public interface TestUserService {
    TestUserDTO.RegisterRes createUser(TestUserDTO.RegisterReq request);
    TestUserDTO.LoginRes login(TestUserDTO.LoginReq request);
    TestUserDTO.TokenInfo reissue(String refreshToken);
    TestUserDTO.logout logout(String accessToken);
}
