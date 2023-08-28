package com.hanium.emoji_pot.domain.users.controller;

import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.dto.*;
import com.hanium.emoji_pot.domain.users.service.UserService;
import com.hanium.emoji_pot.global.exception.ExceptionManager;
import com.hanium.emoji_pot.global.exception.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity register(@Validated @RequestBody UserRegisterRequestDto registerRequest, BindingResult br) throws SQLException {
        log.info("회원 가입 요청 requestDto : {}", registerRequest);

        if (br.hasErrors()) {
            ExceptionManager.ifNullAndBlank();
        }

        UserRegisterResponseDto registerResponse = userService.register(registerRequest);

        return ResponseEntity.ok(Response.success(registerResponse));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody UserLoginRequestDto loginRequest, BindingResult br, HttpServletRequest request) throws SQLException {
        log.info("로그인 요청 requestDto : {}", loginRequest);

        if (br.hasErrors()) {
            ExceptionManager.ifNullAndBlank();
        }

        UserLoginResponseDto loginResponse = userService.login(loginRequest);

        HttpSession session = request.getSession(true);
        session.setAttribute("Email", loginRequest.getEmail());

        return ResponseEntity.ok(Response.success(loginResponse));
    }

    @GetMapping("/info/{username}")
    public Response userInfo(@PathVariable("username") String username) throws SQLException {
        log.info("조회할 사용자 닉네임 : {}", username);

        return Response.success(userService.getUser(username));
    }

    @PatchMapping("/modify/{userId}")
    public ResponseEntity modifyUser(@PathVariable("userId") Long userId, @Validated @RequestBody UserModifyRequestDto userModifyRequest, Authentication authentication) throws SQLException {
        User user = userService.findUserById(userId);
        String requestUserEmail = authentication.getName();
        log.info("사용자 정보 수정 요청자 이메일 : {}", requestUserEmail);

        userService.modifyUser(userModifyRequest, userId, requestUserEmail);
        UserModifyResponseDto userModifyResponse = new UserModifyResponseDto(user);

        return ResponseEntity.ok(Response.success(userModifyResponse));
    }

    @PatchMapping("/password/{userId}")
    public Response modifyPassword(@PathVariable("userId") Long userId, @Validated @RequestBody UserPasswordModifyRequestDto passwordModifyRequest, Authentication authentication) throws SQLException {
        User user = userService.findUserById(userId);
        String requestUserEmail = authentication.getName();
        log.info("비밀번호 수정 요청자 email : {}", requestUserEmail);

        userService.modifyPassword(passwordModifyRequest, userId, requestUserEmail);

        return Response.success("비밀번호 변경 성공");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        return "로그아웃 성공";
    }
}
