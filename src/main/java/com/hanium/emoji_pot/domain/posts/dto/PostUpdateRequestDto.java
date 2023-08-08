package com.hanium.emoji_pot.domain.posts.dto;

import com.hanium.emoji_pot.domain.posts.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestDto {

    @Column(length = 30)
    private String location;

    @NotEmpty
    private Integer emotion;

    @NotEmpty
    private String record;
}
