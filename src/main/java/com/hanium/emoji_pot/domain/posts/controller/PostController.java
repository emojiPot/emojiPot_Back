package com.hanium.emoji_pot.domain.posts.controller;

import com.hanium.emoji_pot.domain.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/v1/posts")
    public void uploadPost() {}

    @PutMapping("/v1/posts/{post_id}")
    public void updatePost() {}

    @DeleteMapping("/v1/posts/{post_id}")
    public void deletePost() {}

    @GetMapping("/v1/posts/{post_id}")
    public void getPost() {}

    @GetMapping("/v1/posts")
    public void getAllPosts() {}

}
