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
    public String loginFail(@RequestParam String errorMsg, Model model) {
        model.addAttribute("errorMsg", errorMsg);

        return "user/login/loginForm";
    }


}

//    /**
//     * 차량 상세정보 조회
//     */
//    @GetMapping("/cars/{carId}")
//    public String carInfo(Model model, @PathVariable("carId") Long id) {
//
//        CarUpdateDto carUpdateDto = carService.getCar(id);
//        model.addAttribute("carUpdateDto", carUpdateDto);
//
//        return "admin/carDetail";
//    }
//
//    /**
//     * 차량 수정
//     */
//    @PostMapping("/cars/" + "edit/{carId}")
//    public String carEdit(@PathVariable Long carId, @PathVariable EnrollStatus status,
//                          @Validated @ModelAttribute CarUpdateDto carUpdateDto, BindingResult result)
//            throws ParseException {
//        if (result.hasErrors()) {
//            return "admin/carDetail";
//        }
//        carUpdateDto.setCar_id(carId);
//        carUpdateDto.setStatus(status);
//
//        carService.carUpdate(carUpdateDto);
//
//        return "redirect:/admin/carList";
//    }