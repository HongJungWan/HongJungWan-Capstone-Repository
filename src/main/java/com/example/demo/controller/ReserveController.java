package com.example.demo.controller;

import com.example.demo.dto.college.CollegeListDto;
import com.example.demo.dto.parking.ParkingReserveDto;
import com.example.demo.dto.reserve.ReserveRequestDto;
import com.example.demo.dto.reserve.ReserveSimpleDto;
import com.example.demo.dto.security.PrincipalDetails;
import com.example.demo.service.reserve.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveService reserveService;

    /**
     * 예약 가능 주차장 조회, 검색
     */

    @GetMapping("/college/list")
    public String collegeList(@AuthenticationPrincipal PrincipalDetails user, Model model,
                              @RequestParam(defaultValue = "noSearch") String addressSearch, @RequestParam(value = "page", defaultValue = "0") int page) {

        if (1 == reserveService.validateDuplicateUser(user.getUsername())) {
            return "user/reserve/duplicate";
        }

        Page<CollegeListDto> collegeList = reserveService.getCollegeList(addressSearch, page);
        model.addAttribute("collegeList", collegeList);
        return "user/reserve/collegeList";
    }

    /**
     * 예약가능백신 조회 및 선택
     */
    @GetMapping("/{collegeId}/parking")
    public String selectParking(
            @PathVariable Long collegeId, Model model) {

        List<ParkingReserveDto> parking = reserveService.getAvailableParkingNameList(collegeId);

        model.addAttribute("parking", parking);
        model.addAttribute("collegeId", collegeId);

        return "user/reserve/reserveSelectForm";
    }

    /**
     * 예약처리
     */
    @PostMapping
    public String reserve(
            @AuthenticationPrincipal PrincipalDetails principal,
            @ModelAttribute ReserveRequestDto reserveRequestDto,
            RedirectAttributes redirectAttributes) {
        log.info("collegeId = {}", reserveRequestDto.getCollegeId());
        log.info("parkingName = {}", reserveRequestDto.getParkingName());

        String username = principal.getUsername();
        log.info("username = {}", username);

        Long savedUserId = reserveService.reserve(
                username,
                reserveRequestDto.getCollegeId(),
                reserveRequestDto.getParkingName()
        );

        return "redirect:/reserve";
    }

    /**
     * 예약조회
     */
    @GetMapping
    public String reserveResult(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
        String username = principal.getUsername();
        log.info("username = {}", username);

        ReserveSimpleDto reserveResult = reserveService.getReserveResult(username);
        if (reserveResult.getCollegeName() == null) {
            return "redirect:/";
        }
        model.addAttribute("reserveResult", reserveResult);
        return "user/reserve/ReserveResult";
    }

    /**
     * 예약취소
     */
    @GetMapping("/{reserveId}/cancel")
    public String cancel(@PathVariable Long reserveId) {
        reserveService.cancelReserve(reserveId);

        return "redirect:/";
    }

}
