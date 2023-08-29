package com.hanium.emoji_pot.domain.comments.dto;

import com.hanium.emoji_pot.domain.comments.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentModifyResponseDto {

    private Long commentId;

    private String content;

    private String username;

    private Long postId;

    private String createdAt;

    private String updatedAt;

    public CommentModifyResponseDto(Comment comment, String username, Long postId) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.username = username;
        this.postId = postId;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.createdAt = sdf.format(comment.getCreatedAt());
        this.updatedAt = sdf.format(comment.getUpdatedAt());
    }

}
