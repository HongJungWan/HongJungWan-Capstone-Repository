package com.recoder.capstone.controller;

import com.recoder.capstone.domain.entity.User;
import com.recoder.capstone.domain.value.Gender;
import com.recoder.capstone.domain.value.Role;
import com.recoder.capstone.dto.security.PrincipalDetails;
import com.recoder.capstone.dto.user.UserAddFormDto;
import com.recoder.capstone.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @ModelAttribute("genders")
    public Gender[] genders(){
        Gender[] genders = Gender.values();
        return genders;
    }

    /**
     *
     * 회원 가입 form 으로 보내줄 Controller. oauth로 로그인한 사람의 계정을 보고
     * 이미 회원 가입된 User면 다시 Home으로 , 아니면 추가정보를 작성하도록
     * password는회원 가입 화면에서 hidden으로 전송 받도록 할 것
     * Role은 그냥 User로
     * 회원 가입 페이지는 joinForm으로
     *
     */
    @GetMapping("/signup")
    public String joinForm(@AuthenticationPrincipal PrincipalDetails details, Model model){

        if(userService.getUserByEmail(details.getUsername())==null){
            UserAddFormDto userAddFormDto=new UserAddFormDto();
            userAddFormDto.setEmail(details.getUsername());
            userAddFormDto.setName(details.getName());
            userAddFormDto.setPassword(details.getPassword());
            model.addAttribute("userAddFormDto",userAddFormDto);
            return "user/signup/signupForm";
        }
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String join(@Validated @ModelAttribute UserAddFormDto form, BindingResult result){
        if(result.hasErrors()) {
            return "user/signup/signupForm";
        }
        User user = User.createUser()
                .email(form.getEmail())
                .password(form.getPassword())
                .name(form.getName())
                .role(Role.ROLE_USER)
                .gender(Gender.valueOf(form.getGender()))
                .age(form.getAge())
                .address(form.getAddress())
                .detailAddress(form.getDetailAddress())
                .build();

        userService.createUser(user);

        return "redirect:/";
    }
}
