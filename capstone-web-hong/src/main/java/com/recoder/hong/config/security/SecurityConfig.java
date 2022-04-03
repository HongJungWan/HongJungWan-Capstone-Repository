package com.recoder.hong.config.security;

import com.recoder.hong.oauth.OAuth2Service;
import com.recoder.hong.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OAuth2Service oauth2Service;
    private final UserService userService;
    private final UserLoginFailHandler userLoginFailHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/adminLogin")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureHandler(userLoginFailHandler)  //loginfail 페이지로 forward될것 postmapping 필요
                .and()
                .logout()// 로그아웃
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/index")
                .defaultSuccessUrl("/signup")  //회원 가입 폼으로 만들 예정 일단 테스트(03/31)
                .userInfoEndpoint()
                .userService(oauth2Service);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }


}
