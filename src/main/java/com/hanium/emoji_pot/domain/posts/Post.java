package com.hanium.emoji_pot.domain.posts;

import com.hanium.emoji_pot.domain.BaseTimeEntity;
import com.hanium.emoji_pot.domain.images.Image;
import com.hanium.emoji_pot.domain.likes.Like;
import com.hanium.emoji_pot.domain.posts.dto.PostRequestDto;
import com.hanium.emoji_pot.domain.posts.dto.PostUpdateRequestDto;
import com.hanium.emoji_pot.domain.users.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 30)
    private String location;

    @Column(nullable = false)
    private Integer emotion;

    @Column(nullable = false)
    private String record;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private Boolean isOpened;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    List<Image> images = new ArrayList<>();

    public Post(String location, Integer emotion, String record, User user) {
        this.location = location;
        this.emotion = emotion;
        this.record = record;
        this.isDeleted = false;
        this.isOpened = true;
        this.user = user;
    }

    public static Post createPost(PostRequestDto postRequest, User user) {
        return new Post(postRequest.getLocation(), postRequest.getEmotion(), postRequest.getRecord(), user);
    }

    public void updatePost(PostUpdateRequestDto postUpdateRequestDto) {
        this.location = postUpdateRequestDto.getLocation();
        this.emotion = postUpdateRequestDto.getEmotion();
        this.record = postUpdateRequestDto.getRecord();
    }

    public void deletePost() {
        this.isDeleted = true;
    }
}
