package com.example.demo.controller;


import com.example.demo.domain.entity.Car;
import com.example.demo.domain.entity.Report;
import com.example.demo.domain.value.CarSearch;
import com.example.demo.dto.college.*;
import com.example.demo.dto.report.ReportListDto;
import com.example.demo.dto.reserve.ReserveWithUsernameDto;
import com.example.demo.dto.security.PrincipalDetails;
import com.example.demo.repository.ReportRepository;
import com.example.demo.service.admin.AdminService;
import com.example.demo.service.admin.AdminServiceImpl;
import com.example.demo.service.admin.CarServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminServiceImpl adminServiceImpl;
    private final CarServiceImpl carServiceImpl;
    private final ReportRepository reportRepository;


    /**
     * 주차장 이름으로 주차장 단건 조회
     */
    @GetMapping("/college")
    @ResponseBody
    public ResponseEntity<CollegeResponseDto> getCollege(@RequestParam("name") String collegeName) {
        CollegeResponseDto collegeResponseDto = adminService.getCollegeInfo(collegeName);

        return ResponseEntity.ok(collegeResponseDto);
    }

    /**
     * 주차장 등록 폼 랜더링
     */
    @GetMapping("/college/add")
    public String collegeForm(Model model) {
        model.addAttribute("collegeRequestDto", new CollegeRequestDto());

        return "admin/collegeRegister";
    }

    /**
     * 현재 어드민이 관리하는 주차장 목록 조회 (주차장이름, 주소만 조회)
     */
    @GetMapping("/colleges")
    @ResponseBody
    public List<CollegeSimpleInfoDto> collegeSimple(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        List<CollegeSimpleInfoDto> colleges = adminService.getAllSimpleCollegeInfo(principal.getName());

        return colleges;
    }

    /**
     * 주차장 등록
     *
     * @param authentication 등록되는 주차장애 admin을 추가해주기 위해 현재 인증 객체를 사용
     */
    @PostMapping("/college/add")
    public String addCollege(
            Authentication authentication,
            @Validated @ModelAttribute CollegeRequestDto form, BindingResult result, HttpServletRequest request) throws Exception {

        if (result.hasErrors()) {
            return "admin/collegeRegister";
        }

        makeParkingInfoMap(form.getA(), form.getB(), form.getC(), form.getD(), form.getParkingInfoMap());

        /**
         * /admin/** 으로 접근되었다는 것은 security filter를 지나 인가된 사용자라는 것. (Role = ADMIN)
         * 따라서 주차장 등록시 Authentication에서 얻어온 유저 정보를 그대로 사용 (주차장에 Admin을 넣어주기 위함)
         */
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        log.info("principal.name = {}", principal.getName());

        adminService.addCollege(form, principal.getName());

        return "redirect:/admin/college/list";
    }

    /**
     * 주차장 목록
     */
    @GetMapping("/college/list")
    public String collegeList(@AuthenticationPrincipal PrincipalDetails principal, Model model,
                              @RequestParam(defaultValue = "noSearch") String addressSearch) {
        String adminName = principal.getName();
        List<CollegeListDto> collegeList = adminService.getCollegeList(adminName, addressSearch);
        model.addAttribute("collegeList", collegeList);
        return "admin/collegeList";
    }

    /**
     * 신고 목록
     */
    @GetMapping("/report/list")
    public String reportList(Model model) {

        List<Report> reportList = reportRepository.findAll();

        List<ReportListDto> collect = reportList.stream()
                .map(m -> new ReportListDto(m.getId(), m.getUser().getName(), m.getCollege().getCollegeName(), m.getCarNumber(), m.getCause(), m.getReportDate(), m.getStatus()))
                .collect(Collectors.toList());

        model.addAttribute("collect", collect);

        return "admin/reportList";
    }

    /**
     * 주차장 상세정보 조회
     */
    @GetMapping("/college/{collegeId}")
    public String collegeInfo(Model model, @PathVariable("collegeId") Long id) {

        CollegeUpdateDto collegeUpdateDto = adminService.getCollege(id);
        model.addAttribute("collegeUpdateDto", collegeUpdateDto);

        return "admin/collegeDetail";
    }

    /**
     * 주차장 수정
     */
    @PostMapping("/college/" + "edit/{collegeId}")
    public String collegeEdit(@PathVariable Long collegeId,
                              @Validated @ModelAttribute CollegeUpdateDto collegeUpdateDto, BindingResult result)
            throws ParseException {
        if (result.hasErrors()) {
            return "admin/collegeDetail";
        }
        collegeUpdateDto.setId(collegeId);
        makeParkingInfoMap(collegeUpdateDto.getA(), collegeUpdateDto.getB(),
                collegeUpdateDto.getC(), collegeUpdateDto.getD(), collegeUpdateDto.getParkingInfoMap());
        adminService.collegeUpdate(collegeUpdateDto);

        return "redirect:/admin/college/list";
    }

    /**
     * 예약 현황 조회
     */
    @GetMapping("/college/reserves/{collegeId}")
    public String reserveCondition(@PathVariable Long collegeId, Model model) {
        List<ReserveWithUsernameDto> reserveConditions = adminService.getReserveCondition(collegeId);

        model.addAttribute("reserveConditions", reserveConditions);
        return "admin/reserveCondition";
    }

    // parkingInfoMap만드는 메서드
    private void makeParkingInfoMap(Integer a, Integer b, Integer c, Integer d, Map<String, Integer> parkingInfoMap) {

        if (a != null && a != 0) {
            parkingInfoMap.put("장애인 전용 구역", a); // 0509 수정
        }
        if (b != null && b != 0) {
            parkingInfoMap.put("전기차 전용 구역", b);
        }
        if (c != null && c != 0) {
            parkingInfoMap.put("경차 전용 구역", c);
        }
        if (d != null && d != 0) {
            parkingInfoMap.put("어르신 우선 구역", d);
        }
    }

    @GetMapping("/car/list")
    public String carList(@ModelAttribute("carSearch") CarSearch carSearch, Model model) {
        List<Car> cars = carServiceImpl.findAll(carSearch);
        model.addAttribute("cars", cars);

        return "admin/carList";
    }

    /**
     * 차량 등록 취소
     */
    @PostMapping("/car/{car_id}/cancel")
    public String cancelCar(@PathVariable("car_id") Long car_id) {
        carServiceImpl.cancelCar(car_id);

        return "redirect:/admin/car/list";
    }

    /**
     * 차량 등록
     */
    @PostMapping("/car/{car_id}/register")
    public String registerCar(@PathVariable("car_id") Long car_id) {
        carServiceImpl.registerCar(car_id);

        return "redirect:/admin/car/list";
    }

    /**
     * 주차장 등록 취소(hidden)
     */
    @PostMapping("/college/{collegeId}/hidden")
    public String cancelCollege(@PathVariable("collegeId") Long collegeId) {
        adminServiceImpl.cancelCollege(collegeId);

        return "redirect:/admin/college/list";
    }

}
