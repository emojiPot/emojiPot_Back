package com.hanium.emoji_pot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostIdAndIsDeleted(Long postId, Boolean isDeleted);
    List<Post> findAllByIsDeleted(Boolean isDeleted);
    List<Post> findAllByLocationAndIsDeleted(String location, Boolean isDeleted);
}