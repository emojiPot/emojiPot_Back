package com.hanium.emoji_pot.domain.posts.dto;

import com.hanium.emoji_pot.domain.comments.dto.CommentListDto;
import com.hanium.emoji_pot.domain.images.Image;
import com.hanium.emoji_pot.domain.images.dto.ImageUploadDto;
import com.hanium.emoji_pot.domain.posts.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
