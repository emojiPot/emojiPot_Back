package com.hanium.emoji_pot.domain.Member.service;


import com.hanium.emoji_pot.PrincipalDetails;
import com.hanium.emoji_pot.domain.Member.Member;
import com.hanium.emoji_pot.domain.Member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member principal = userRepository.findByUsername(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다 : " + username);
                });
        return new PrincipalDetails(principal); // 시큐리티 세션에 userDetails 타입으로 저장
    }

}
