package com.hanium.emoji_pot.domain.users.controller;

import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.dto.UserLoginRequestDto;
import com.hanium.emoji_pot.domain.users.dto.UserLoginResponseDto;
import com.hanium.emoji_pot.domain.users.dto.UserRegisterRequestDto;
import com.hanium.emoji_pot.domain.users.dto.UserRegisterResponseDto;
import com.hanium.emoji_pot.domain.users.service.UserService;
import com.hanium.emoji_pot.global.exception.ExceptionManager;
import com.hanium.emoji_pot.global.exception.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        log.info("🎉 회원 가입 요청 requestDto : {}", registerRequest);

        if (br.hasErrors()) {
            ExceptionManager.ifNullAndBlank();
        }

        UserRegisterResponseDto registerResponse = userService.register(registerRequest);

        return ResponseEntity.ok(Response.success(registerResponse));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody UserLoginRequestDto loginRequest, BindingResult br, HttpServletRequest request) throws SQLException {
        log.info("🎉 로그인 요청 requestDto : {}", loginRequest);

        if (br.hasErrors()) {
            ExceptionManager.ifNullAndBlank();
        }

        UserLoginResponseDto loginResponse = userService.login(loginRequest);

        HttpSession session = request.getSession(true);
        session.setAttribute("Email", loginRequest.getEmail());

        return ResponseEntity.ok(Response.success(loginResponse));
    }

//    @PostMapping("/{user_id}/role/change")
//    public ResponseEntity changeRole(@PathVariable(name = "user_id") Long userId) {
//        log.info("🎉 관리자가 등급을 변경할 회원 id : {} ", userId);
//
//        userService.changeRole(userId);
//
//        UserRoleChangeResponseDto responseDto = new UserRoleChangeResponseDto(userId, userId + "번 아이디의 권한을 변경하였습니다.");
//
//        return ResponseEntity.ok(Response.success(responseDto));
//    }
}
