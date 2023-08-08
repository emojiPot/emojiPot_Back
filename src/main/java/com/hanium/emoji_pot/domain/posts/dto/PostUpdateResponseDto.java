package com.hanium.emoji_pot.domain.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateResponseDto {

    private Long postId;

    private String message;

    public PostUpdateResponseDto (Long postId) {
        this.postId = postId;
        this.message = "게시물 수정 완료";
    }
}
