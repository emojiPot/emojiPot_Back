package com.hanium.emoji_pot.domain.users.service;

import com.hanium.emoji_pot.domain.security.JwtTokenUtil;
import com.hanium.emoji_pot.domain.users.User;
import com.hanium.emoji_pot.domain.users.UserRepository;
import com.hanium.emoji_pot.global.error.ErrorCode;
import com.hanium.emoji_pot.global.error.ErrorController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expireTime = 1000 * 60 * 60; // 만료시간 1시간(ms단위)

    public User register(User user) {
        userRepository.findByUsernameAndIsDeleted(user.getUsername(), user.getIsDeleted())
                .ifPresent( user1 -> {
                    throw new ErrorController(ErrorCode.DUPLICATED_USER_NAME, String.format("Username : %s",user1.getUsername()));
                });
        userRepository.save(user);

        return user;
    }

    public User findUserEntity(String email) {
        return userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new ErrorController(ErrorCode.USER_NOT_FOUNDED, ""));
    }


    public String login(String email, String password) {
        User user = userRepository.findByEmailAndIsDeleted(email, false)
                .orElseThrow(() -> new ErrorController(ErrorCode.USER_NOT_FOUNDED, String.format("존재하지 않는 이메일입니다.", email)));
        if(!encoder.matches(password,user.getPassword())){
            throw new ErrorController(ErrorCode.INVALID_PASSWORD, String.format("비밀번호를 잘못 입력하였습니다."));
        }
        return JwtTokenUtil.createToken(email, secretKey, expireTime);
    }

}
