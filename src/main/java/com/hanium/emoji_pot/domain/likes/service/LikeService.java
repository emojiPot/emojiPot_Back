package com.hanium.emoji_pot.domain.likes.service;

import com.hanium.emoji_pot.domain.likes.Like;
import com.hanium.emoji_pot.domain.likes.LikeRepository;
import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.posts.PostRepository;
import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.UserRepository;
import com.hanium.emoji_pot.global.exception.AppException;
import com.hanium.emoji_pot.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Boolean addLike(Long postId, String requestEmail) throws SQLException {
        User requestUser = userValid(requestEmail);
        Post post = postValid(postId);
        Optional<Like> like = likeValid(requestUser, post);

        if (like.isPresent()) {
            likeRepository.deleteById(like.get().getLikeId());
            return true;
        } else {
            likeRepository.save(Like.addLike(requestUser, post));
            return false;
        }
    }

    @Transactional
    public Boolean isLiked(Long postId, String requestEmail) throws SQLException {
        User requestUser = userValid(requestEmail);
        Post post = postValid(postId);
        Optional<Like> like = likeValid(requestUser, post);

        if (like.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public User userValid(String email) {
        return userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public Post postValid(Long postId) {
        Post post = postRepository.findByPostIdAndIsDeleted(postId, false)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if (post.getIsDeleted()) {
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }
        return post;
    }

    public Optional<Like> likeValid(User user, Post post) {
        Optional<Like> like = likeRepository.findByUserAndPost(user, post);
        return like;
    }
}
