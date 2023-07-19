package com.hanium.emoji_pot.domain;


import javax.persistence.*;
import java.sql.Timestamp;


//데이터베이스와 매핑될 엔티티클래스 작성
@Entity
@Table(name="user_table", schema="emoji_pot")
public class user_table {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long User_id;

    @Column
    private String username,name, email,password;
    @Column
    private String profile_image;

    @Column
    private String Introduce;

    @Column
    private Timestamp created_at;

    @Column
    private Timestamp update_at;

    @Column
    private Integer is_deleted;

    public Long getUser_id(){
        return User_id;
    }
    public void setUser_id(Long User_id){
        this.User_id = User_id;

    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getName(){
        return  name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getIntroduce(){
        return Introduce;
    }
    public void setInproduce(String Introduce){
        this.Introduce = Introduce;
    }

    public String getProfile_image(){
        return profile_image;
    }
    public void setProfile_image(String profile_image){
        this.profile_image = profile_image;
    }

    public Integer getIs_deleted(){
        return is_deleted;
    }
    public void setIs_deleted(Integer is_deleted){
        this.is_deleted = is_deleted;
    }

    public Timestamp getCreated_at(){
        return created_at;
    }
    public void setCreated_at(){
        this.created_at = created_at;
    }

    public Timestamp getUpdate_at(){
        return update_at;
    }
    public void setUpdate_at(){
        this.update_at = update_at;
    }
}