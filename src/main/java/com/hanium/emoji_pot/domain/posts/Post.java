package com.hanium.emoji_pot.domain.posts;

import com.hanium.emoji_pot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private Long userId;

    @Column(length = 30)
    private String location;

    private Integer emotion;

    private String record;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Builder
    public Post(Long userId, String location, Integer emotion, String record) {
        this.userId = userId;
        this.location = location;
        this.emotion = emotion;
        this.record = record;
        this.isDeleted = false;
    }
}
