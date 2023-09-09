package com.hanium.emoji_pot.domain.Member.dto;

import com.hanium.emoji_pot.domain.Member.Member;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public class UserUpdateDto {
    private String profile_image;
    private String introduce;
    private String updated_at;
    private Integer is_deleted;

    @Builder
    public UserUpdateDto(String profile_image, String introduce,
                         String updated_at, Integer is_deleted) {

        this.profile_image = profile_image;
        this.introduce = introduce;
        this.updated_at = updated_at;
        this.is_deleted = is_deleted;
    }
    public void update(UserUpdateDto userUpdateDto) {

    }

    public Member toEntity(){
        return Member.builder()
                .profile_image(profile_image)
                .introduce(introduce)
                .is_deleted(is_deleted)
                .build();
    }
}
