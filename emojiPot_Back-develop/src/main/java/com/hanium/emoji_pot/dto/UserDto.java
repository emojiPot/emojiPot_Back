package com.hanium.emoji_pot.dto;


import com.hanium.emoji_pot.domain.user_table;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


//회원가입 버튼 클릭 시 보내는 Body 값
@Data
public class UserDto {
    private Long User_id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String profile_image;
    private String Introduce;
    private Timestamp created_at;
    private Timestamp update_at;
    private Integer is_deleted;


    public UserDto (user_table user){
        name = user.getName();
        username = user.getUsername();
        password = user.getPassword();
        email = user.getEmail();
        profile_image = user.getProfile_image();
        Introduce = user.getIntroduce();
        created_at = user.getCreated_at();
        update_at = user.getUpdate_at();
        is_deleted = user.getIs_deleted();
    }

}
