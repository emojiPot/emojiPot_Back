package com.hanium.emoji_pot.domain.posts.service;

import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.posts.PostRepository;
import com.hanium.emoji_pot.domain.posts.dto.PostRequestDto;
import com.hanium.emoji_pot.domain.posts.dto.PostResponseDto;
import com.hanium.emoji_pot.domain.posts.dto.PostUpdateRequestDto;
import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostResponseDto savePost(PostRequestDto postRequest) {
        User user = userService.findUserEntity(postRequest.getUserId());
        Post entity = postRepository.save(postRequest.toEntity(user));
        return PostResponseDto.of(entity);
    }

    public Post findEntity(Long postId) {
        return postRepository.findByPostIdAndIsDeleted(postId, false)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequest) {
        Post entity = findEntity(postId);
        entity.updatePost(postUpdateRequest);
        return PostResponseDto.of(entity);
    }

    @Transactional
    public void deletePost(Long postId) {
        findEntity(postId).deletePost();
    }

    public PostResponseDto findByPostId(Long postId) {
        return PostResponseDto.of(findEntity(postId));
    }
}
