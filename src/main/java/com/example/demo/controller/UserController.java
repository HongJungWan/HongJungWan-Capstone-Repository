package com.example.demo.controller;

import com.example.demo.dto.security.PrincipalDetails;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    /**
     *
     * 회원 가입 form 으로 보내줄 Controller.
     * oauth로 로그인한 사람의 계정 확인
     * 1. 어플에서 이미 회원 가입된 User만 예약 기능 사용 가능,
     * 2. 어플에서 회원 가입한 이메일로 예약 기능 사용 가능
     */
    
    @GetMapping("/signup")
    public String joinForm(@AuthenticationPrincipal PrincipalDetails details, Model model){

        /*회원이 존재하지 않을 때*/
        if(userService.getUserByEmail(details.getUsername())==null){

            return "user/signup/signinFail";
        }

        /*회원이 존재할 때*/
        return "redirect:/";
    }

}
