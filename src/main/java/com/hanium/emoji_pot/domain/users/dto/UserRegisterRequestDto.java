package com.hanium.emoji_pot.domain.users.dto;

import com.hanium.emoji_pot.domain.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserRegisterRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public User toEntity(String password) {
        return User.builder()
                .name(name)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
