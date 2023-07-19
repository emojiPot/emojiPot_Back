package com.hanium.emoji_pot.Controller;

import com.hanium.emoji_pot.dto.UserDto;
import com.hanium.emoji_pot.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//회원가입과 로그인 구현

@Controller
public class UserController {
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    //회원가입 페이지 출력 요청
    @GetMapping("/v1/user/register")
    public String saveForm(){
        return "user_save";
    }
    //회원가입 요청 처리
    @PostMapping("/v1/user/register")
    public String save(@RequestParam("email") String email){
        System.out.println("email = " + email);
        System.out.println("UserController.java");
        return "user_save";
    }
}
