package com.hanium.emoji_pot.domain.posts.dto;

import com.hanium.emoji_pot.domain.posts.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PostResponseDto {

    private Long postId;

    private String userName;

    private String location;

    private Integer emotion;

    private String record;

    private LocalDateTime updatedAt;

    public static PostResponseDto of (Post entity) {
        return PostResponseDto.builder()
                .postId(entity.getPostId())
                .userName(entity.getUser().getUsername())
                .location(entity.getLocation())
                .emotion(entity.getEmotion())
                .record(entity.getRecord())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
