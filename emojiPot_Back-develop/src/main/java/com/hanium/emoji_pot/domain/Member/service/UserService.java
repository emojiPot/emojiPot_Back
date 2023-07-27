package com.hanium.emoji_pot.domain.Member.service;



import com.hanium.emoji_pot.domain.Member.Member;
import com.hanium.emoji_pot.domain.Member.dto.UserDto;
import com.hanium.emoji_pot.domain.Member.repository.UserRepository;
import lombok.AllArgsConstructor;
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
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    //중복회원 체크
    public boolean joinCheck(UserDto userDto) {
        Optional<Member> findMember = userRepository.findByEmail(userDto.getEmail());
        if(findMember == null){
            return true;
        }else{
            return false;
        }
    }

    //회원가입
    @Transactional //정상적으로 종료되면 트랜잭션 커밋 -> 데이터베이스에 변경 사항 반영
    public void signup(UserDto userDto) throws ParseException{
        // 중복 회원 체크
        if (!joinCheck(userDto)) {
            throw new RuntimeException("이미 가입된 회원입니다.");
        }else{
            Member member = userDto.toEntity();
            member.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(member);
        }
    }

   /*
    public Member login(UserDto userDto) throws  Exception{
        Member findMember = userRepository.findByEmail(userDto.getEmail());
        if (findMember != null) {
            String encodedPassword = findMember.getPassword();
            if (passwordEncoder.matches(userDto.getPassword(), encodedPassword)) {
                return findMember; // 로그인 성공
            }
        }
        throw new RuntimeException("인증에 실패했습니다. 올바른 이메일과 비밀번호를 입력해주세요."); // 인증 실패
    } */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = userRepository.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다: " + username);
        }

        // Spring Security의 UserDetails 인터페이스를 구현한 객체를 반환해야 합니다.
        return new org.springframework.security.core.userdetails.User(
                member.getUsername(),
                member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}

