package com.hanium.emoji_pot.domain.Member.service;



import com.hanium.emoji_pot.PrincipalDetails;
import com.hanium.emoji_pot.domain.Member.Member;
import com.hanium.emoji_pot.domain.Member.dto.UserDto;
import com.hanium.emoji_pot.domain.Member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Collections;


@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //중복회원 체크
    public boolean joinCheck(UserDto userDto) {
        Member findMember = userRepository.findByEmail(userDto.getEmail());
        if(findMember != null){
            return false;
        }else{
            return true;
        }
    }

    //회원가입
    @Transactional //정상적으로 종료되면 트랜잭션 커밋 -> 데이터베이스에 변경 사항 반영
    public void signup(UserDto userDto) throws ParseException{
        if (userDto == null) {
            throw new IllegalArgumentException("회원 정보가 null입니다.");
        }

        String password = userDto.getPassword();
        if (password == null) {
            throw new IllegalArgumentException("비밀번호가 null입니다.");
        }

        if (!joinCheck(userDto)) {
            throw new RuntimeException("이미 가입된 회원입니다.");
        } else {
            Member member = userDto.toEntity();

            if (passwordEncoder == null) {
                throw new IllegalStateException("PasswordEncoder가 주입되지 않았습니다.");
            }

            member.setPassword(passwordEncoder.encode(password));

            if (userRepository == null) {
                throw new IllegalStateException("UserRepository가 주입되지 않았습니다.");
            }

            userRepository.save(member);
        }
    }

}

