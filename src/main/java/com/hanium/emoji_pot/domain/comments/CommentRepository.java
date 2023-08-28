package com.hanium.emoji_pot.domain.comments;

import com.hanium.emoji_pot.domain.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostAndParentIsNullOrderByCreatedAtDesc(Post post);

    List<Comment> findByPostAndParentCommentId(Post post, Long parentCommentId);
}
