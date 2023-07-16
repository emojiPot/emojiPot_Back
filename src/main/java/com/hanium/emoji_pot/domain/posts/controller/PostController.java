package com.hanium.emoji_pot.domain.posts.controller;

import com.hanium.emoji_pot.domain.posts.dto.PostRequestDto;
import com.hanium.emoji_pot.domain.posts.dto.PostResponseDto;
import com.hanium.emoji_pot.domain.posts.dto.PostUpdateRequestDto;
import com.hanium.emoji_pot.domain.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto savePost(@Validated @RequestBody PostRequestDto postRequest) {
        return postService.savePost(postRequest);
    }

    @PatchMapping("/{post_id}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto postUpdateRequest) {
        return postService.updatePost(postId, postUpdateRequest);
    }
}
