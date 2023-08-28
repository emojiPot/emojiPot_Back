package com.hanium.emoji_pot.domain.posts.dto;

import com.hanium.emoji_pot.domain.posts.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;

@AllArgsConstructor
@Getter
public class PostDetailDto {

    private Long postId;

    private String username;

    private String location;

    private Integer emotion;

    private String record;

    private String createdAt;

    private String updatedAt;

    private int likeNum;

    public PostDetailDto(Post post) {
        this.postId = post.getPostId();
        this.location = post.getLocation();
        this.emotion = post.getEmotion();
        this.record = post.getRecord();
        this.username = post.getUser().getUsername();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.createdAt = sdf.format(post.getCreatedAt());
        this.updatedAt = sdf.format((post.getUpdatedAt()));
        this.likeNum = post.getLikes().size();
    }
}
