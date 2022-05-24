package com.example.demo.controller;

import com.example.demo.dto.college.CollegeListUserDto;
import com.example.demo.dto.security.PrincipalDetails;
import com.example.demo.service.reserve.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveService reserveService;

    /**
     * 예약 가능 주차장 조회
     */
    @GetMapping("/colleges")
    public String collegeList(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit, Model model,
            @AuthenticationPrincipal PrincipalDetails user) {

        reserveService.validateDuplicateUser(user.getUsername());
        List<CollegeListUserDto> collegeListDtos = reserveService.getAllCollegeInfo(offset, limit);

        model.addAttribute("collegeList", collegeListDtos);

        return "user/reserve/collegeList";

    }

    /**
     * 예약 가능 주차장 주소로 검색
     */
    @GetMapping("/search")
    public String searchByAddress(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit, Model model,
            @RequestParam String addressSearch) {

        List<CollegeListUserDto> collegeListDtos = reserveService.getAllCollegeInfoSearchByAddress(addressSearch, offset, limit);
        model.addAttribute("collegeList", collegeListDtos);

        return "user/reserve/collegeList";

    }


}
