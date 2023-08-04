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
    

    public User register(User user) {
        userRepository.findByUsernameAndIsDeleted(user.getUsername(), user.getIsDeleted())
                .ifPresent( user1 -> {
                    throw new ErrorController(ErrorCode.DUPLICATED_USER_NAME, String.format("Username : %s",user1.getUsername()));
                });
        userRepository.save(user);

        return user;
    }


}
