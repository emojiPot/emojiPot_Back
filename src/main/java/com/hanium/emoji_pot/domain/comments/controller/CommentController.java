package com.hanium.emoji_pot.domain.comments.controller;

import com.hanium.emoji_pot.domain.comments.dto.CommentRequestDto;
import com.hanium.emoji_pot.domain.comments.dto.CommentResponseDto;
import com.hanium.emoji_pot.domain.comments.dto.ReCommentRequestDto;
import com.hanium.emoji_pot.domain.comments.dto.ReCommentResponseDto;
import com.hanium.emoji_pot.domain.comments.service.CommentService;
import com.hanium.emoji_pot.global.exception.ExceptionManager;
import com.hanium.emoji_pot.global.exception.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity saveComment(@PathVariable("postId") Long postId, @Validated @RequestBody CommentRequestDto commentRequest, BindingResult br, Authentication authentication) throws SQLException {
        log.info("댓글을 작성하려는 게시글 id : {} || requestDto : {}", postId, commentRequest);

        if (br.hasErrors()) {
            return ExceptionManager.ifNullAndBlank();
        }

        String requestUserEmail = authentication.getName();

        log.info("댓글 작성 요청자 Email : {} ", requestUserEmail);

        CommentResponseDto commentResponse = commentService.saveComment(commentRequest, requestUserEmail, postId);

        return ResponseEntity.ok(Response.success(commentResponse));
    }

    @GetMapping("/{postId}/comments")
    public Response getAllCommentsByPostId(@PathVariable("postId") Long postId) throws SQLException {
        log.info("댓글 조회할 게시글 id : {}", postId);

        return Response.success(commentService.getAllCommentsByPostId(postId));
    }

    @PostMapping("/{postId}/comments/{commentId}/recomments")
    public ResponseEntity createReplyComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long parentCommentId, @Validated @RequestBody ReCommentRequestDto replyCommentRequest, BindingResult br, Authentication authentication) throws SQLException {
        log.info("대댓글을 작성하려는 게시글 id : {} 댓글 id : {}", postId, parentCommentId);
        log.info("대댓글 작성 requestDto : {}", replyCommentRequest);

        if (br.hasErrors()) {
            return ExceptionManager.ifNullAndBlank();
        }

        String requestUserEmail = authentication.getName();
        log.info("대댓글 작성 요청자 Email : {}", requestUserEmail);

        ReCommentResponseDto responseDto = commentService.saveReComment(replyCommentRequest, requestUserEmail, postId, parentCommentId);

        return ResponseEntity.ok(Response.success(responseDto));
    }

    @GetMapping("/{postId}/comments/{commentId}/recomments")
    public Response getAllReCommentsByCommentId(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) throws SQLException {
        log.info("대댓글 조회할 게시글 id : {} 댓글 id : {}", postId, commentId);

        return Response.success(commentService.getAllReCommentsByCommentId(postId, commentId));
    }

}
