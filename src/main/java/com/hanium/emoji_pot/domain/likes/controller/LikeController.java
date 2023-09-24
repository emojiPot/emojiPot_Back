package com.hanium.emoji_pot.domain.likes.controller;

import com.hanium.emoji_pot.domain.likes.service.LikeService;
import com.hanium.emoji_pot.global.exception.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}/likes")
    public Response addLike(@PathVariable("postId") Long postId, Authentication authentication) throws SQLException {
        String requestEmail = authentication.getName();
        log.info("좋아요를 누른 게시글 id : {} || 좋아요 요청자 Email : {}", postId, requestEmail);

        Boolean adding = likeService.addLike(postId, requestEmail);
        if (adding) {
            return Response.success("좋아요를 취소했습니다.");
        } else {
            return Response.success("좋아요를 눌렀습니다.");
        }
    }

    @GetMapping("/{postId}/likes")
    public Response isLiked(@PathVariable("postId") Long postId, Authentication authentication) throws SQLException {
        String requestEmail = authentication.getName();
        log.info("좋아요를 눌렀는지 확인할 게시글 id : {} || 확인 요청자 Email : {}", postId, requestEmail);

        Boolean isLiked = likeService.isLiked(postId, requestEmail);
        log.info("사용자의 게시글 좋아요 여부 : {}", isLiked);
        if (isLiked) {
            return Response.success(true);
        } else {
            return Response.success(false);
        }
    }
}
