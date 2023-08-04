package com.hanium.emoji_pot.domain.users.dto;

import com.hanium.emoji_pot.domain.users.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegisterResponseDto {

    private String name;

    private String username;

    private String email;

    public static UserRegisterResponseDto of (User entity) {
        return UserRegisterResponseDto.builder()
                .name(entity.getName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .build();
    }


}
