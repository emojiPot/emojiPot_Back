package com.hanium.emoji_pot.domain.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestDto {

    @Column(length = 30)
    private String location;

    private Integer emotion;

    private String record;

}
