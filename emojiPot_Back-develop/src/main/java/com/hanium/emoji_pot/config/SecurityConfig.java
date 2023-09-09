package com.hanium.emoji_pot.config;

import com.hanium.emoji_pot.PrincipalDetails;
import com.hanium.emoji_pot.domain.Member.dto.UserDto;
import com.hanium.emoji_pot.domain.Member.service.PrincipalDetailService;
import com.hanium.emoji_pot.domain.Member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity //시큐리티 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한 및 인증을 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean  //회원가입시 사용
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder()); // passwordEncoder 하는 애가 encodePWD 임.
    }

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        http .csrf().disable()
                .authorizeHttpRequests()
                     .antMatchers("/css/**").permitAll() //인증절차 없이 설정한 리소스들은 접근 가능하게 함
                     .and()
                .formLogin() // 로그인 설정
                     .loginPage("/v1/user/login")
                     .loginProcessingUrl("/v1/user/loginProc") //스프링 시큐리티가 낚아챔
                     .defaultSuccessUrl("/")
                     .usernameParameter("email")
                     .permitAll()
                     .and()
                .logout()
                     .logoutRequestMatcher(new AntPathRequestMatcher("/v1/user/logout"))
                     .logoutSuccessUrl("/")
                     .invalidateHttpSession(true) //세션 초기화
                     .and()
                     .exceptionHandling();
    }
}
