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

    @DeleteMapping("/{post_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @GetMapping("/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.findByPostId(postId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDto> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/location")
    public List<PostResponseDto> getByLocation(@RequestParam String location) {
        return postService.findAllByLocation(location);
    }

}
