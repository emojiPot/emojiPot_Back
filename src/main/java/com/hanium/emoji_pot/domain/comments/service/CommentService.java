package com.hanium.emoji_pot.domain.comments.service;

import com.hanium.emoji_pot.domain.comments.Comment;
import com.hanium.emoji_pot.domain.comments.CommentRepository;
import com.hanium.emoji_pot.domain.comments.dto.*;
import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.posts.PostRepository;
import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.UserRepository;
import com.hanium.emoji_pot.domain.users.UserRole;
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

        return new CommentResponseDto(comment, user.getUsername(), postId);
    }

    // 게시글의 댓글 조회
    public List<CommentListDto> getAllCommentsByPostId(Long postId) throws SQLException {
        Post post = postValid(postId);
        return commentRepository.findByPostAndParentIsNullOrderByCreatedAtDesc(post).stream().map(CommentListDto::new).collect(Collectors.toList());
    }

    // 댓글, 대댓글 수정
    @Transactional
    public CommentModifyResponseDto modifyComment(CommentModifyRequestDto commentModifyRequest, Long postId, Long commentId, String requestUserEmail) throws SQLException {
        User user = userValid(requestUserEmail);
        postValid(postId);
        Comment comment = commentValid(commentId);
        UserRole requestUserRole = user.getRole();
        String author = comment.getUser().getEmail();
        String authorName = comment.getUser().getUsername();

        log.info("댓글 수정 요청자 ROLE = {} 댓글 작성자 닉네임 = {}", requestUserRole, authorName);

        checkAuth(requestUserEmail, author, requestUserRole);
        comment.modifyComment(commentModifyRequest.getContent());

        return new CommentModifyResponseDto(commentRepository.saveAndFlush(comment), user.getUsername(), postId);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long postId, Long commentId, String requestUserEmail) throws SQLException {
        User requestUser = userValid(requestUserEmail);
        postValid(postId);
        Comment comment = commentValid(commentId);
        UserRole requestUserRole = requestUser.getRole();
        String author = comment.getUser().getEmail();
        String authorName = comment.getUser().getUsername();

        log.info("댓글 삭제 요청자 ROLE = {} 댓글 작성자 닉네임 = {}", requestUserRole, authorName);

        checkAuth(requestUserEmail, author, requestUserRole);

        commentRepository.delete(comment);
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

    // 댓글의 대댓글 조회
    public List<CommentListDto> getAllReCommentsByCommentId(Long postId, Long commentId) throws SQLException {
        Post post = postValid(postId);
        Comment comment = commentValid(commentId);

        return commentRepository.findByPostAndParentCommentId(post, commentId).stream().map(CommentListDto::new).collect(Collectors.toList());
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

    public void checkAuth(String requestEmail, String author, UserRole requestUserRole) {
        if (!requestUserRole.equals(UserRole.ROLE_ADMIN) && !author.equals(requestEmail)) {
            throw new AppException(ErrorCode.USER_NOT_MATCH);
        }
    }

}
