package com.hanium.emoji_pot.domain.posts;

import com.hanium.emoji_pot.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostIdAndIsDeleted(Long postId, Boolean isDeleted);
    List<Post> findAllByIsDeletedOrderByCreatedAtDesc(Boolean isDeleted);
    List<Post> findAllByLocationAndIsDeletedOrderByCreatedAtDesc(String location, Boolean isDeleted);
    List<Post> findAllByUserAndIsDeletedOrderByCreatedAtDesc(User user, Boolean isDeleted);
}
