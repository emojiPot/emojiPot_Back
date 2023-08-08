package com.hanium.emoji_pot.domain.posts.dto;

import com.hanium.emoji_pot.domain.posts.Post;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostResponseDto {

    private String username;

    private Long postId;

    private String message;

    public PostResponseDto (Post post) {
        this.username = post.getUser().getUsername();
        this.postId = post.getPostId();
        this.message = "게시물 등록 완료";
    }
}
