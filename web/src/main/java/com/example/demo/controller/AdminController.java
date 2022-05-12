package com.example.demo.controller;


import com.example.demo.domain.entity.Car;
import com.example.demo.dto.collage.*;
import com.example.demo.dto.reserve.ReserveWithUsernameDto;
import com.example.demo.dto.security.PrincipalDetails;
import com.example.demo.repository.CarSearch;
import com.example.demo.service.admin.AdminService;
import com.example.demo.service.admin.AdminServiceImpl;
import com.example.demo.service.admin.CarService;
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

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminServiceImpl adminServiceImpl;
    private final CarService carService;

    /**
     * 주차장 이름으로 주차장 단건 조회
     */
    @GetMapping("/collage")
    @ResponseBody
    public ResponseEntity<CollageResponseDto> getCollage(@RequestParam("name") String collageName) {
        CollageResponseDto collageResponseDto = adminService.getCollageInfo(collageName);

        return ResponseEntity.ok(collageResponseDto);
    }


    /**
     * 주차장 등록 폼 랜더링
     */
    @GetMapping("/collage/add")
    public String collageForm(Model model) {
        model.addAttribute("collageRequestDto", new CollageRequestDto());
        return "admin/collageRegister";
    }

    /**
     * 현재 어드민이 관리하는 주차장 목록 조회 (주차장이름, 주소만 조회)
     */
    @ResponseBody
    @GetMapping("/collages")
    public List<CollageSimpleInfoDto> asd(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        List<CollageSimpleInfoDto> collages = adminService.getAllSimpleCollageInfo(principal.getName());
        return collages;
    }


    /**
     * 주차장 등록
     *
     * @param authentication 등록되는 주차장애 admin을 추가해주기 위해 현재 인증 객체를 사용
     */
    @PostMapping("/collage/add")
    public String addCollage(
            Authentication authentication,
            @Validated @ModelAttribute CollageRequestDto form, BindingResult result, HttpServletRequest request) throws Exception {

        if (result.hasErrors()) {
            return "admin/collageRegister";
        }

        makeParkingInfoMap(form.getA(), form.getB(), form.getC(), form.getD(), form.getParkingInfoMap());

        /**
         * /admin/** 으로 접근되었다는 것은 security filter를 지나 인가된 사용자라는 것. (Role = ADMIN)
         * 따라서 주차장 등록시 Authentication에서 얻어온 유저 정보를 그대로 사용 (주차장에 Admin을 넣어주기 위함)
         */
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        log.info("principal.name = {}", principal.getName());

        adminService.addCollage(form, principal.getName());

        return "redirect:/admin/collage/list";
    }

    /**
     * 주차장 목록
     */
    @GetMapping("/collage/list")
    public String collageList(@AuthenticationPrincipal PrincipalDetails principal, Model model,
                              @RequestParam(defaultValue = "noSearch") String addressSearch) {
        String adminName = principal.getName();
        List<CollageListDto> collageList = adminService.getCollageList(adminName, addressSearch);
        model.addAttribute("collageList", collageList);
        return "admin/collageList";
    }

    /**
     * 주차장 상세정보 조회
     */
    @GetMapping("/collage/{collageId}")
    public String collageInfo(Model model, @PathVariable("collageId") Long id) {

        CollageUpdateDto collageUpdateDto = adminService.getCollage(id);
        model.addAttribute("collageUpdateDto", collageUpdateDto);

        return "admin/collageDetail";
    }

    /**
     * 주차장 수정
     */
    @PostMapping("/collage/" + "edit/{collageId}")
    public String collageEdit(@PathVariable Long collageId,
                              @Validated @ModelAttribute CollageUpdateDto collageUpdateDto, BindingResult result)
            throws ParseException {
        if (result.hasErrors()) {
            return "admin/collageDetail";
        }
        collageUpdateDto.setId(collageId);
        makeParkingInfoMap(collageUpdateDto.getA(), collageUpdateDto.getB(),
                collageUpdateDto.getC(), collageUpdateDto.getD(), collageUpdateDto.getParkingInfoMap());
        adminService.collageUpdate(collageUpdateDto);

        return "redirect:/admin/collage/list";
    }

    /**
     * 예약 현황 조회
     */
    @GetMapping("/collage/reserves/{collageId}")
    public String reserveCondition(@PathVariable Long collageId, Model model) {
        List<ReserveWithUsernameDto> reserveConditions = adminService.getReserveCondition(collageId);

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
        List<Car> cars = carService.findAll(carSearch);
        model.addAttribute("cars", cars);

        return "admin/carList";
    }

    /**
     * 차량 등록 취소
     */
    @PostMapping("/car/{car_id}/cancel")
    public String cancelCar(@PathVariable("car_id") Long car_id) {
        carService.cancelCar(car_id);
        return "redirect:/admin/car/list";
    }

}
