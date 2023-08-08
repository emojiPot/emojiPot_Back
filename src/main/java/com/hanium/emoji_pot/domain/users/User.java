package com.hanium.emoji_pot.domain.users;

import com.hanium.emoji_pot.domain.BaseTimeEntity;
import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.users.dto.UserModifyRequestDto;
import com.hanium.emoji_pot.domain.users.dto.UserRegisterRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isDeleted = false;
        this.role = UserRole.ROLE_USER;
    }

    public static User createUser(UserRegisterRequestDto registerRequest, String password) {
        return new User(registerRequest.getName(), registerRequest.getUsername(), registerRequest.getEmail(), password);
    }
    public void changeRole() {
        if (this.role.equals(UserRole.ROLE_USER)) {
            this.role = UserRole.ROLE_ADMIN;
        } else {
            this.role = UserRole.ROLE_USER;
        }
    }

    public void modifyUser(UserModifyRequestDto userModifyRequest) {
        this.username = userModifyRequest.getUsername();
        this.profileImage = userModifyRequest.getProfileImage();
        this.introduce = userModifyRequest.getIntroduce();
    }

}
