package com.hanium.emoji_pot.domain.users.service;

import com.hanium.emoji_pot.domain.security.JwtTokenUtil;
import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.UserRepository;
import com.hanium.emoji_pot.domain.users.UserRole;
import com.hanium.emoji_pot.domain.users.dto.*;
import com.hanium.emoji_pot.global.exception.AppException;
import com.hanium.emoji_pot.global.exception.ErrorCode;
import com.hanium.emoji_pot.global.exception.ErrorController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    // 회원가입
    @Transactional
    public UserRegisterResponseDto register(UserRegisterRequestDto registerRequest) throws SQLException {

        String requestUserEmail = registerRequest.getEmail();

        userJoinValid(requestUserEmail);

        String encodedPassword = encoder.encode(registerRequest.getPassword());

        User saved = userRepository.save(User.createUser(registerRequest, encodedPassword));

        return new UserRegisterResponseDto(saved);
    }

    public void userJoinValid(String email) {
        userRepository.findByEmailAndIsDeleted(email, false)
                .ifPresent(user -> {throw new AppException(ErrorCode.DUPLICATED_USER_NAME);});

    }

    public User userValid(String email) {
        return userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    // 사용자 권한 변경
    @Transactional
    public void changeRole(Long userId) {
        User found = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        log.info("{}", found);

        found.changeRole();
    }

    public UserRole findRoleByUserName(String userName) {
        User foundUser = userValid(userName);
        return foundUser.getRole();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new ErrorController(ErrorCode.USER_NOT_FOUND, ""));
    }

    // 로그인
    public UserLoginResponseDto login(UserLoginRequestDto loginRequest) throws SQLException {
        String requestEmail = loginRequest.getEmail();
        String requestPassword = loginRequest.getPassword();

        User found = userValid(requestEmail);

        if (!encoder.matches(requestPassword, found.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "잘못된 비밀번호 입니다");
        }

        return new UserLoginResponseDto(JwtTokenUtil.createToken(requestEmail, found.getUserId(), secretKey));
    }

    // 사용자 상세 조회
    public UserDetailDto getUser(String username) throws SQLException {
        User user = findUserByUsername(username);

        return new UserDetailDto(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsernameAndIsDeleted(username, false)
                .orElseThrow(() -> new ErrorController(ErrorCode.USER_NOT_FOUND, ""));
    }
}
