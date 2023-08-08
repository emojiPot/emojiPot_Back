package com.hanium.emoji_pot.domain.users.dto;

import com.hanium.emoji_pot.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserDetailDto {

    private String name;

    private String username;

    private String email;

    private String profile_image;

    private String introduce;

    public UserDetailDto(User user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profile_image = user.getProfileImage();
        this.introduce = user.getIntroduce();
    }

}
