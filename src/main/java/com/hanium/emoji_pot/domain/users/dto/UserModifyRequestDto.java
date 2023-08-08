package com.hanium.emoji_pot.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserModifyRequestDto {

    private String username;

    private String profileImage;

    @Column(length = 100)
    private String introduce;
}
