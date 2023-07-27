package com.hanium.emoji_pot.domain.posts.dto;

import com.hanium.emoji_pot.domain.posts.Post;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
public class PostUpdateRequestDto {

    @Column(length = 30)
    private String location;

    @NotEmpty
    private Integer emotion;

    @NotEmpty
    private String record;

    public Post toEntity() {
        return Post.builder()
                .location(location)
                .emotion(emotion)
                .record(record)
                .build();
    }
}
