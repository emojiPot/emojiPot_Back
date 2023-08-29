package com.hanium.emoji_pot.domain.comments.dto;

import com.hanium.emoji_pot.domain.comments.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommentListDto {

    private Long CommentId;

    private String content;

    private String username;

    private Long postId;

    private String createdAt;

    private List<CommentListDto> reComments ;

    private int reCommentsSize = 0;

    public CommentListDto(Comment comment) {
        this.CommentId = comment.getCommentId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getPostId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.createdAt = sdf.format(comment.getCreatedAt());
    }
}
