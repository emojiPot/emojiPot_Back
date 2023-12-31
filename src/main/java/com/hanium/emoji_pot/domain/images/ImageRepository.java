package com.hanium.emoji_pot.domain.images;

import com.hanium.emoji_pot.domain.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByPost(Post post);

    List<Image> findAll();
}
