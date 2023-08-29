package com.hanium.emoji_pot.domain.comments.service;

import com.hanium.emoji_pot.domain.comments.Comment;
import com.hanium.emoji_pot.domain.comments.CommentRepository;
import com.hanium.emoji_pot.domain.comments.dto.*;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 작성
    @Transactional
    public CommentResponseDto saveComment(CommentRequestDto commentRequest, String requestUserEmail, Long postId) throws SQLException {
        User user = userValid(requestUserEmail);
        Post post = postValid(postId);

        Comment comment = Comment.createComment(commentRequest.getContent(), user, post);
        commentRepository.save(comment);

        return new CommentResponseDto(comment, requestUserEmail, postId);
    }

    // 게시글의 댓글 조회
    public List<CommentListDto> getAllCommentsByPostId(Long postId) throws SQLException {
        Post post = postValid(postId);
        return commentRepository.findByPostAndParentIsNullOrderByCreatedAtDesc(post).stream().map(CommentListDto::new).collect(Collectors.toList());
    }

    // 대댓글 작성
    @Transactional
    public ReCommentResponseDto saveReComment(ReCommentRequestDto replyCommentRequest, String requestUserEmail, Long postId, Long parentCommentId) throws SQLException {
        User user = userValid(requestUserEmail);
        Post post = postValid(postId);
        Comment parentComment = commentValid(parentCommentId);

        Comment comment = Comment.createReComment(replyCommentRequest.getReCommentContent(), user, post, parentComment);
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
