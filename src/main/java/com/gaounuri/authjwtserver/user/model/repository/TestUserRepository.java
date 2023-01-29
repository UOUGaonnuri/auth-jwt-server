package com.gaounuri.authjwtserver.user.model.repository;

import com.gaounuri.authjwtserver.user.model.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestUserRepository extends JpaRepository<TestUser, Long> {
    Optional<TestUser> findTestUserByUserEmail(String userEmail);
    Boolean existsTestUserByUserNameOrUserEmail(String userName, String userEmail);
}
