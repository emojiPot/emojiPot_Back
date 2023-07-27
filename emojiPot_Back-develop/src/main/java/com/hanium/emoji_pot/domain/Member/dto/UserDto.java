package com.hanium.emoji_pot.domain.Member.dto;

import com.hanium.emoji_pot.domain.Member.Member;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


//회원가입 시 전달되는 정보를 담아 서비스에 전달
//dto -> entity
@Data
@NoArgsConstructor
public class UserDto {
    private Long user_id;

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String profile_image;
    private String introduce;
    private String created_at;
    private String updated_at;
    private Integer is_deleted;

    @Builder
    public UserDto(Long user_id, String name, String username, String email, String password,
                   String profile_image, String introduce, String created_at, String updated_at, Integer is_deleted) {
        this.user_id = user_id;
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


    public Member toEntity() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date createdAt =  sdf.parse(created_at);
        Date updateAt = sdf.parse(updated_at);

        return Member.builder()
                .name(name)
                .username(username)
                .email(email)
                .password(password)
                .profile_image(profile_image)
                .introduce(introduce)
                .created_at(createdAt)
                .updated_at(updateAt)
                .is_deleted(is_deleted)
                .build();
    }
}
