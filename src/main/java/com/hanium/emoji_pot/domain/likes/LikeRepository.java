package com.hanium.emoji_pot.domain.likes;

import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
