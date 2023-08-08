package com.hanium.emoji_pot.domain.posts.controller;

import com.hanium.emoji_pot.domain.posts.dto.PostRequestDto;
import com.hanium.emoji_pot.domain.posts.dto.PostResponseDto;
import com.hanium.emoji_pot.domain.posts.dto.PostUpdateRequestDto;
import com.hanium.emoji_pot.domain.posts.dto.PostUpdateResponseDto;
import com.hanium.emoji_pot.domain.posts.service.PostService;
import com.hanium.emoji_pot.domain.users.service.UserService;
import com.hanium.emoji_pot.global.exception.ExceptionManager;
import com.hanium.emoji_pot.global.exception.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity savePost(@Validated @RequestBody PostRequestDto postRequest, BindingResult br, Authentication authentication) throws SQLException {
        log.info("📝게시글 작성 requestDto : {}", postRequest);

        if (br.hasErrors()) {
            ExceptionManager.ifNullAndBlank();
        }

        String requestUserEmail = authentication.getName();
        log.info("작성 요청자 username : {}", requestUserEmail);

        PostResponseDto postResponse = postService.savePost(postRequest, requestUserEmail);
        return ResponseEntity.ok(Response.success(postResponse));
    }

}
