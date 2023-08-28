package com.hanium.emoji_pot.domain.posts.dto;

import com.hanium.emoji_pot.domain.posts.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;

@Getter
@AllArgsConstructor
public class PostListDto {

    private Long postId;

    private String username;

    private String location;

    private String record;

    private String createdAt;

    private String updatedAt;

    private int likeNum;

    public PostListDto(Post post) {
        this.postId = post.getPostId();
        this.username = post.getUser().getUsername();
        this.location = post.getLocation();
        this.record = post.getRecord();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.createdAt = sdf.format(post.getCreatedAt());
        this.updatedAt = sdf.format((post.getUpdatedAt()));
        this.likeNum = post.getLikes().size();
    }
}
