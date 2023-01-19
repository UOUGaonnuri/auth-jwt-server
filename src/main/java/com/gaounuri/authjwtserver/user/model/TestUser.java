package com.gaounuri.authjwtserver.user.model;

import com.gaounuri.authjwtserver.constant.entity.BaseEntity;
import com.gaounuri.authjwtserver.user.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "test_user_jwt")
@Data
@RequiredArgsConstructor
@Builder
public class TestUser extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name="user_email", unique = true)
    private String userEmail;

    @Column(name ="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "varchar(15) default 'ROLE_USER'")
    private Role role;

}
