package com.hanium.emoji_pot.domain.Member.Controller;

import com.hanium.emoji_pot.domain.Member.Member;
import com.hanium.emoji_pot.domain.Member.dto.UserDto;
import com.hanium.emoji_pot.domain.Member.dto.UserUpdateDto;
import com.hanium.emoji_pot.domain.Member.repository.UserRepository;
import com.hanium.emoji_pot.domain.Member.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;



@Controller
public class UserController {
    private final UserService userService;
    private final SimpleDateFormat sdf;

    //의존성 주입 (UserController 생성시 UserService 를 주입 /UserRepository null 오류 방지 )
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }


    @Operation(summary = "회원가입")
    //회원가입 요청 처리
    @PostMapping("/v1/user/register")
    public String save(@RequestBody UserDto userDto) throws ParseException {
        userService.signup(userDto);
        return "회원가입 성공";
    }



    //로그인
    // 스프링 시큐리티가 해당 주소를 낚아챈다.
    @GetMapping("/v1/user/login")
    public String login(){
        return "login";
    }

    //비밀번호 수정



    //회원정보보기



    //회원정보 수정


    //유저 정보 가져오기
    @Operation(summary = "회원정보 수정")
    @GetMapping("/v1/user/{user_id}")
    public void BasicInfo(@RequestBody UserUpdateDto userUpdateDto) throws  Exception{

    }

    @PutMapping("/v1/user/{user_id}")
    public void updateBasicInfo(@PathVariable Long id ,@RequestBody UserUpdateDto userUpdateDto){


    }




}
