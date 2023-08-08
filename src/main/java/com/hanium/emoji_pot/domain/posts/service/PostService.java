package com.hanium.emoji_pot.domain.posts.service;

import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.posts.PostRepository;
import com.hanium.emoji_pot.domain.posts.dto.PostRequestDto;
import com.hanium.emoji_pot.domain.posts.dto.PostResponseDto;
import com.hanium.emoji_pot.domain.posts.dto.PostUpdateRequestDto;
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

    // 게시글 목록 조회
    public User userValid(String email) {
        return userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public Post postValid(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

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
