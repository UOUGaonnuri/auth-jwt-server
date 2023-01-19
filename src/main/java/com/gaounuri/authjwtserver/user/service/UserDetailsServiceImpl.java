package com.gaounuri.authjwtserver.user.service;

import com.gaounuri.authjwtserver.constant.enums.ErrorCode;
import com.gaounuri.authjwtserver.constant.exception.CustomException;
import com.gaounuri.authjwtserver.user.model.TestUser;
import com.gaounuri.authjwtserver.user.model.repository.TestUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TestUserRepository testUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TestUser user = testUserRepository.findTestUserByUserName(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }
}
