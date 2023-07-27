package com.hanium.emoji_pot.domain.posts.dto;

import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    @NotNull
    private Long userId;

    @Column(length = 30)
    private String location;

    @NotBlank
    private Integer emotion;

    @NotBlank
    private String record;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .location(location)
                .emotion(emotion)
                .record(record)
                .build();
    }

}
