package com.hanium.emoji_pot.domain.users.dto;

import com.hanium.emoji_pot.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
