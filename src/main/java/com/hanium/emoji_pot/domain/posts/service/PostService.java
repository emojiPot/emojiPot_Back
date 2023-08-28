package com.hanium.emoji_pot.domain.posts.service;

import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.posts.PostRepository;
import com.hanium.emoji_pot.domain.posts.dto.*;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 작성
    @Transactional
    public PostResponseDto savePost(PostRequestDto requestDto, String requestEmail) throws SQLException {
        User requestUser = userValid(requestEmail);
        Post post = Post.createPost(requestDto, requestUser);
        return new PostResponseDto(postRepository.save(post));
    }

    // 게시글 수정
    @Transactional
    public void updatePost(PostUpdateRequestDto postUpdateRequest, Long postId, String requestEmail) throws SQLException {
        User requestUser = userValid(requestEmail);
        Post post = postValid(postId);

        UserRole requestUserRole = requestUser.getRole();
        String author = post.getUser().getEmail();
        log.info("게시글 수정 요청자 ROLE = {} 게시글 작성자 author = {}", requestUserRole, author);

        checkAuth(requestEmail, author, requestUserRole);

        post.updatePost(postUpdateRequest);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, String requestEmail) throws SQLException {
        User requestUser = userValid(requestEmail);
        Post post = postValid(postId);

        UserRole requestUserRole = requestUser.getRole();
        String author = post.getUser().getEmail();

        log.info("게시글 수정 요청자 ROLE = {} 게시글 작성자 author = {}", requestUserRole, author);

        checkAuth(requestEmail, author, requestUserRole);
        post.deletePost();
    }

    // 게시글 상세 조회
    public PostDetailDto getPostById(Long postId) throws SQLException {
        Post foundPost = postValid(postId);

        return new PostDetailDto(foundPost);
    }

    // 게시글 장소로 검색
    @Transactional
    public List<PostListDto> getPostByLocation(String location) throws SQLException {
        return postRepository.findAllByLocationAndIsDeletedOrderByUpdatedAtDesc(location, false).stream().map(PostListDto::new).collect(Collectors.toList());
    }


    public User userValid(String email) {
        return userRepository.findByEmailAndIsDeleted(email, false).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public Post postValid(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if (post.getIsDeleted() == true) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }
        return post;
    }

    public void checkAuth(String requestEmail, String author, UserRole requestUserRole) {
        if (!requestUserRole.equals(UserRole.ROLE_ADMIN) && !author.equals(requestEmail)) {
            throw new AppException(ErrorCode.USER_NOT_MATCH);
        }
    }

}
