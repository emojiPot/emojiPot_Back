package com.hanium.emoji_pot.domain.Member;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

//Security 의 내부적으로 사용하는 클래스의 이름이 User 이므로
//Accout 나 Member 라는 이름 사용해서 구현해야함 .
//데이터베이스와 매핑될 엔티티클래스 작성

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name="user", schema="emoji_pot")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String profile_image;

    @Column(length = 100)
    private String introduce;

    @CreationTimestamp
    @Column(nullable = false)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date updated_at;

    @Column
    private Integer is_deleted;

    @Builder
    public Member(Long user_id, String name, String username, String email, String password, String profile_image,
                  String introduce, Date created_at, Date updated_at, Integer is_deleted) {
        this.user_id= user_id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profile_image = profile_image;
        this.introduce = introduce;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_deleted = is_deleted;
    }
}