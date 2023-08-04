package com.hanium.emoji_pot.domain.users;

import com.hanium.emoji_pot.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name; // 실명

    @Column(nullable = false, unique = true)
    private String username; // 닉네임

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    private String profileImage; // 프로필 사진

    @Column(length = 100)
    private String introduce; // 간단한 소개

    private Boolean isDeleted;

    @Builder
    public User(String name, String username, String email, String password, String profileImage, String introduce) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.introduce = introduce;
        this.isDeleted = false;
    }

}
