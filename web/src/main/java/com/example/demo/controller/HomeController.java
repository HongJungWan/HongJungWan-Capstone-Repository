package com.example.demo.controller;

import com.example.demo.dto.security.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails details) {

        return "index";
    }

    @GetMapping("/adminLogin")
    public String loginForm() {
        return "user/login/loginForm";
    }

    @PostMapping("/loginFail")
    public String loginFail(@RequestParam String errorMsg, Model model){
        model.addAttribute("errorMsg",errorMsg);

        return "user/login/loginForm";
    }


}
