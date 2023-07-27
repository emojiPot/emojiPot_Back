package com.hanium.emoji_pot.domain.Member.Controller;

import com.hanium.emoji_pot.domain.Member.Member;
import com.hanium.emoji_pot.domain.Member.dto.UserDto;
import com.hanium.emoji_pot.domain.Member.dto.UserUpdateDto;
import com.hanium.emoji_pot.domain.Member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    //client -> 컨트롤러에서 Dto 로 받은 후 service 로 전달
    //회원가입 페이지 출력 요청
    @GetMapping("/v1/user/register")
    public String saveForm(@ModelAttribute UserDto userDto){
        return "/user/signupForm";
    }

    //회원가입 요청 처리
    @PostMapping("/v1/user/register")
    public String save(@RequestBody  UserDto userDto) throws ParseException {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = "+ userDto);
        userService.signup(userDto);
        return "회원가입 성공";
    }

    //회원정보 수정
    @PutMapping("/v1/user/{user_id}")
    public void updateBasicInfo(@RequestBody UserUpdateDto userUpdateDto) throws  Exception{
        userUpdateDto.update(userUpdateDto);
    }

    //비밀번호 수정

    //로그인 페이지 출력
    @GetMapping("/v1/user/login")
    public String loginForm() {
        return "/user/loginForm";
    }

    /*
    //로그인 요청처리
    @PostMapping("/v1/user/login")
    public String login(@RequestParam UserDto userDto, HttpSession session) throws Exception {
        Member authenticatedMember = userService.login(userDto);
        if (authenticatedMember != null) {
            // 로그인 성공시 세션에 사용자 정보 저장
            session.setAttribute("authenticatedUser", authenticatedMember);
            return "redirect:/"; // 로그인 성공시 이동할 페이지
        } else {
            // 로그인 실패시 로그인 페이지로 다시 이동
            return "redirect:/v1/user/login";
        }
    }
    //로그아웃
    @GetMapping("/v1/user/logout")
    public String logout(HttpSession session) {
        // 세션에서 사용자 정보 삭제
        session.removeAttribute("authenticatedUser");
        return "redirect:/"; // 로그아웃 후 이동할 페이지
    } */
}
