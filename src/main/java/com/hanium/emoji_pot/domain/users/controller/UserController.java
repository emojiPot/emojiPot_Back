package com.hanium.emoji_pot.domain.users.controller;

import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.dto.UserLoginRequestDto;
import com.hanium.emoji_pot.domain.users.dto.UserLoginResponseDto;
import com.hanium.emoji_pot.domain.users.dto.UserRegisterRequestDto;
import com.hanium.emoji_pot.domain.users.dto.UserRegisterResponseDto;
import com.hanium.emoji_pot.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponseDto register(@Validated @RequestBody UserRegisterRequestDto registerRequest) {
        User register = userService.register(registerRequest.toEntity(encoder.encode(registerRequest.getPassword())));
        return UserRegisterResponseDto.of(register);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseDto login(@Validated @RequestBody UserLoginRequestDto loginRequest) {
        String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return new UserLoginResponseDto(token);
    }
}
