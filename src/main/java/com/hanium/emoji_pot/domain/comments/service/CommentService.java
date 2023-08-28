package com.hanium.emoji_pot.domain.comments.service;

import com.hanium.emoji_pot.domain.comments.Comment;
import com.hanium.emoji_pot.domain.comments.CommentRepository;
import com.hanium.emoji_pot.domain.comments.dto.CommentRequestDto;
import com.hanium.emoji_pot.domain.comments.dto.CommentResponseDto;
import com.hanium.emoji_pot.domain.comments.dto.ReCommentRequestDto;
import com.hanium.emoji_pot.domain.comments.dto.ReCommentResponseDto;
import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.posts.PostRepository;
import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.UserRepository;
import com.hanium.emoji_pot.global.exception.AppException;
import com.hanium.emoji_pot.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto saveComment(CommentRequestDto commentRequest, String requestUserEmail, Long postId) throws SQLException {
        User user = userValid(requestUserEmail);
        Post post = postValid(postId);

        Comment comment = Comment.createComment(commentRequest.getContent(), user, post);
        commentRepository.save(comment);

        return new CommentResponseDto(comment, requestUserEmail, postId);
    }

    @Transactional
    public ReCommentResponseDto writeReplyComment(ReCommentRequestDto replyCommentRequest, String requestUserEmail, Long postId, Long parentCommentId) throws SQLException {
        User user = userValid(requestUserEmail);
        Post post = postValid(postId);
        Comment parentComment = commentValid(parentCommentId);

        Comment comment = Comment.createReplyComment(replyCommentRequest.getReplyContent(), user, post, parentComment);
        commentRepository.save(comment);

        return new ReCommentResponseDto(comment, requestUserEmail, postId, parentCommentId);
    }

    public User userValid(String email) {
        return userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public Post postValid(Long postId) {
        Post post = postRepository.findByPostIdAndIsDeleted(postId, false)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if (post.getIsDeleted()) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }
        return post;
    }

    public Comment commentValid(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
    }

}
