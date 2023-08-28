package com.hanium.emoji_pot.domain.likes;

import com.hanium.emoji_pot.domain.BaseTimeEntity;
import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.users.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`like`")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public static Like addLike(User user, Post post) {
        return new Like(user, post);
    }
}
