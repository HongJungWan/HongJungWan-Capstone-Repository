package com.recoder.capstone.controller;

import com.recoder.capstone.dto.college.CollegeListDto;
import com.recoder.capstone.dto.parking.ParkingReserveDto;
import com.recoder.capstone.dto.reserve.AvailableDateDto;
import com.recoder.capstone.dto.reserve.AvailableTimeDto;
import com.recoder.capstone.dto.reserve.ReserveItemRequestDto;
import com.recoder.capstone.dto.reserve.ReserveItemSimpleDto;
import com.recoder.capstone.dto.security.PrincipalDetails;
import com.recoder.capstone.service.reserve.ReserveItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ReserveItemService reserveItemService;

    /**
     * 예약가능 주차장 조회
     */
    @GetMapping("/colleges")
    public String collegeList(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit, Model model,
            @AuthenticationPrincipal PrincipalDetails user) {

        reserveItemService.validateDuplicateUser(user.getUsername());
        List<CollegeListDto> collegeListDtos = reserveItemService.getAllCollegeInfo(offset, limit);
        model.addAttribute("collegeList", collegeListDtos);
        return "user/reserve/collegeList";
    }

    /**
     * 예약가능 주차장 주소로 검색
     */
    @GetMapping("/search")
    public String searchByAddress(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit, Model model,
            @RequestParam String addressSearch) {
        List<CollegeListDto> collegeListDtos = reserveItemService.getAllCollegeInfoSearchByAddress(addressSearch, offset, limit);
        model.addAttribute("collegeList", collegeListDtos);
        return "user/reserve/collegeList";
    }

    /**
     * 예약가능날짜 조회 및 선택
     */
    @GetMapping("/{collegeId}/dates")
    public String getAvailableDates(@PathVariable Long collegeId, Model model) {
        // 주차장이름으로 해당 주차장의 예약가능날짜 조회
        List<AvailableDateDto> availableDates = reserveItemService.getAvailableDates(collegeId);
        model.addAttribute("collegeId", collegeId);
        model.addAttribute("dates", availableDates);

        return "user/reserve/reserveDateSelectForm";
    }

    /**
     * 예약가능시간 조회 및 선택
     */
    @GetMapping("/{collegeId}/times")
    public String getAvailableTimes(
            @PathVariable Long collegeId,
            @RequestParam(name="date") Long selectedDateId, Model model) {
        // 선택한 예약날짜의 pk로 예약가능시간 조회
        List<AvailableTimeDto> times = reserveItemService.getAvailableTimes(selectedDateId);
        model.addAttribute("date", selectedDateId);
        model.addAttribute("times", times);
        return "user/reserve/reserveTimeSelectForm";
    }

    /**
     * 예약가능주차장 구역 조회 및 선택
     */
    @GetMapping("/{collegeId}/parking") public String selectParking
    (
            @PathVariable Long collegeId,
            @RequestParam(name = "date") Long selectedDateId,
            @RequestParam(name = "time") Long selectedTimeId, Model model) {

        List<ParkingReserveDto> parkings = reserveItemService.getAvailableParkingNameList(collegeId);

        model.addAttribute("parkings", parkings);
        model.addAttribute("date", selectedDateId);
        model.addAttribute("time", selectedTimeId);
        model.addAttribute("collegeId", collegeId);

        return "user/reserve/reserveparkingSelectForm";
    }

    /**
     * 예약처리
     */
    @PostMapping
    public String reserve(
            @AuthenticationPrincipal PrincipalDetails principal,
            @ModelAttribute ReserveItemRequestDto reserveItemRequestDto,
            RedirectAttributes redirectAttributes) {
        log.info("collegeId = {}", reserveItemRequestDto.getCollegeId());
        log.info("parkingName = {}", reserveItemRequestDto.getParkingName());
        log.info("reserveDateId = {}", reserveItemRequestDto.getReserveDateId());
        log.info("reserveTimeId = {}", reserveItemRequestDto.getReserveTimeId());

        String username = principal.getUsername();
        log.info("username = {}", username);

        Long savedUserId = reserveItemService.reserve(
                username,
                reserveItemRequestDto.getCollegeId(),
                reserveItemRequestDto.getParkingName(),
                reserveItemRequestDto.getReserveDateId(),
                reserveItemRequestDto.getReserveTimeId()
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

        ReserveItemSimpleDto reserveResult = reserveItemService.getReserveResult(username);
        if (reserveResult.getCollegeName() == null) {
            return "redirect:/";
        }
        model.addAttribute("reserveResult", reserveResult);
        return "user/reserve/ReserveResult";
    }

    /**
     * 예약취소
     */
    @GetMapping("/{reserveItemId}/cancel")
    public String cancel(@PathVariable Long reserveItemId) {
        reserveItemService.cancelReserveItem(reserveItemId);

        return "redirect:/";
    }
}
