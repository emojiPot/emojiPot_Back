package com.hanium.emoji_pot.domain.posts.controller;

import com.hanium.emoji_pot.domain.posts.dto.*;
import com.hanium.emoji_pot.domain.posts.service.PostService;
import com.hanium.emoji_pot.global.exception.ExceptionManager;
import com.hanium.emoji_pot.global.exception.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.sql.SQLException;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity savePost(@Validated @RequestBody PostRequestDto postRequest, BindingResult br, Authentication authentication) throws SQLException {
        log.info("게시글 작성 requestDto : {}", postRequest);

        if (br.hasErrors()) {
            ExceptionManager.ifNullAndBlank();
        }

        String requestUserEmail = authentication.getName();
        log.info("작성 요청자 Email : {}", requestUserEmail);

        PostResponseDto postResponse = postService.savePost(postRequest, requestUserEmail);
        return ResponseEntity.ok(Response.success(postResponse));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity modify(@PathVariable("postId") Long postId, @Validated @RequestBody PostUpdateRequestDto postUpdateRequest, BindingResult br, Authentication authentication) throws SQLException {
        log.info("수정하려는 게시글 id : {} || requestDto : {}", postId, postUpdateRequest);

        if (br.hasErrors()) {
            ExceptionManager.ifNullAndBlank();
        }

        String requestUserEmail = authentication.getName();
        log.info("게시글 수정 요청자 email : {}", requestUserEmail);

        postService.updatePost(postUpdateRequest, postId, requestUserEmail);
        PostUpdateResponseDto postUpdateResponse = new PostUpdateResponseDto(postId);

        return ResponseEntity.ok(Response.success(postUpdateResponse));
    }

    @DeleteMapping("/{postId}")
    public Response delete(@PathVariable("postId") Long postId, Authentication authentication) throws SQLException {

        String requestUserEmail = authentication.getName();
        log.info("삭제 하려는 게시글 id : {} || 삭제 요청자 : {}", postId, requestUserEmail);

        postService.deletePost(postId, requestUserEmail);

        PostDeleteResponseDto postDeleteResponse = new PostDeleteResponseDto(postId);

        return Response.success(postDeleteResponse);
    }

    @GetMapping("/{postId}")
    public Response getPost(@PathVariable("postId") Long postId) throws SQLException {
        log.info("조회할 게시글 id : {}", postId);

        return Response.success(postService.getPostById(postId));
    }

    @GetMapping("/search")
    public Response searchByLocation(@RequestParam(value = "location") String location) throws SQLException {
        return Response.success(postService.getPostByLocation(location));
    }

    @GetMapping("")
    public Response getAllPosts() throws SQLException {
        return Response.success(postService.getAllPosts());
    }

    @GetMapping("/mine")
    public Response getMyPosts(Authentication authentication) throws SQLException {
        String requestUserName = authentication.getName();
        log.info("피드 조회 요청자 : {}", requestUserName);

        return Response.success(postService.getMyPosts(requestUserName));
    }

    @PatchMapping("/{postId}/open")
    public Response isOpen(@PathVariable("postId") Long postId, Authentication authentication) throws SQLException {
        String requestEmail = authentication.getName();
        log.info("공개 요청한 게시글 id : {} 공개 요청자 Email : {}", postId, requestEmail);

        postService.isOpen(postId, requestEmail);

        return Response.success("공개 설정 되었습니다.");
    }

    @PatchMapping("/{postId}/notOpen")
    public Response isNotOpen(@PathVariable("postId") Long postId, Authentication authentication) throws SQLException {
        String requestEmail = authentication.getName();
        log.info("비공개 요청한 게시글 id : {} 비공개 요청자 Email : {}", postId, requestEmail);

        postService.isNotOpen(postId, requestEmail);

        return Response.success("비공개 설정 되었습니다.");
    }

}
