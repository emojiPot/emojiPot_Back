package com.hanium.emoji_pot.domain.users.dto;

import com.hanium.emoji_pot.domain.users.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegisterResponseDto {

    private String name;

    private String username;

    private String email;

    public UserRegisterResponseDto (User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

}
