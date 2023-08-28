package com.hanium.emoji_pot.domain.comments;

import com.hanium.emoji_pot.domain.BaseTimeEntity;
import com.hanium.emoji_pot.domain.likes.Like;
import com.hanium.emoji_pot.domain.posts.Post;
import com.hanium.emoji_pot.domain.users.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public Comment(String content, User user, Post post, Comment parent) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.parent = parent;
    }

    public static Comment createComment(String content, User user, Post post) {
        return new Comment(content, user, post);
    }

    public static Comment createReplyComment(String content, User user, Post post, Comment parent) {
        return new Comment(content, user, post, parent);
    }

    public void modifyComment(String newComment) {
        this.content = newComment;
    }
}
