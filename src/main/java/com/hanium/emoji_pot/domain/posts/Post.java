package com.hanium.emoji_pot.domain.posts;

import com.hanium.emoji_pot.domain.BaseTimeEntity;
import com.hanium.emoji_pot.domain.posts.dto.PostRequestDto;
import com.hanium.emoji_pot.domain.posts.dto.PostUpdateRequestDto;
import com.hanium.emoji_pot.domain.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 30)
    private String location;

    @Column(nullable = false)
    private Integer emotion;

    @Column(nullable = false)
    private String record;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    public Post(User user, String location, Integer emotion, String record) {
        this.user = user;
        this.location = location;
        this.emotion = emotion;
        this.record = record;
        this.isDeleted = false;
    }

    public void deletePost() {
        this.isDeleted = true;
    }

    public void updatePost(PostUpdateRequestDto postUpdateRequestDto) {
        this.location = postUpdateRequestDto.getLocation();
        this.emotion = postUpdateRequestDto.getEmotion();
        this.record = postUpdateRequestDto.getRecord();
    }
}
