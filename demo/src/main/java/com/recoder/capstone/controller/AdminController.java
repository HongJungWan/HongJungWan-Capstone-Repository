package com.recoder.capstone.controller;

import com.recoder.capstone.dto.college.*;
import com.recoder.capstone.dto.reserve.ReserveItemWithUsernameDto;
import com.recoder.capstone.dto.security.PrincipalDetails;
import com.recoder.capstone.service.admin.AdminService;
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
    public String collegeForm(Model model){
        model.addAttribute("collegeRequestDto",new CollegeRequestDto());
        return "admin/collegeRegister";
    }

    /**
     * 현재 어드민이 관리하는 주차장 목록 조회 (주차장이름, 주소만 조회)
     */
    @ResponseBody
    @GetMapping("/colleges")
    public List<CollegeSimpleInfoDto> asd(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        List<CollegeSimpleInfoDto> colleges = adminService.getAllSimpleCollegeInfo(principal.getName());
        return colleges;
    }

    /**
     * 주차장 등록
     * @param authentication 등록되는 주차장애 admin을 추가해주기 위해 현재 인증 객체를 사용
     */
    @PostMapping("/college/add")
    public String addCollege(
            Authentication authentication,
            @Validated @ModelAttribute CollegeRequestDto form, BindingResult result, HttpServletRequest request) throws Exception{

        if(result.hasErrors()){
            return "admin/collegeRegister";
        }

        makeParkingInfoMap(form.getA(), form.getB(), form.getC(), form.getD(), form.getParkingInfoMap());

        timeParse(form);
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
                               @RequestParam(defaultValue = "noSearch")String addressSearch) {
        String adminName = principal.getName();
        List<CollegeListDto> collegeList = adminService.getCollegeList(adminName,addressSearch);
        model.addAttribute("collegeList", collegeList);
        return "admin/collegeList";
    }

    /**
     * 주차장 상세정보 조회
     */
    @GetMapping("/college/{collegeId}")
    public String collegeInfo(Model model,@PathVariable("collegeId")Long id){

        CollegeUpdateDto collegeUpdateDto = adminService.getCollege(id);
        model.addAttribute("collegeUpdateDto", collegeUpdateDto);

        return "admin/collegeDetail";
    }

    /**
     * 주차장 수정
     */
    @PostMapping("/college/" +
            "edit/{collegeId}")
    public String collegeEdit(@PathVariable Long collegeId,
                               @Validated @ModelAttribute CollegeUpdateDto collegeUpdateDto, BindingResult result)
            throws ParseException {
        if(result.hasErrors()){
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
    public String reserveCondition(@PathVariable Long collegeId, Model model){
        List<ReserveItemWithUsernameDto> reserveItemConditions = adminService.getReserveItemCondition(collegeId);

        model.addAttribute("reserveItemConditions",reserveItemConditions);
        return "admin/reserveCondition";
    }

    // 시간을 parseInt 되도록 만드는 메서드
    private void timeParse(CollegeRequestDto form) {
        form.setStartTime(form.getStartTime().split(":")[0]);
        form.setEndTime(form.getEndTime().split(":")[0]);
    }

    // parkingInfoMap만드는 메서드
    private void makeParkingInfoMap(Integer a, Integer b, Integer c, Integer d, Map<String,Integer> parkingInfoMap) {

        if(a !=null && a !=0){
            parkingInfoMap.put("A 구역", a); // A구역 이런식으로 아래도 싹다 바꿔야함
        }
        if(b !=null && b !=0){
            parkingInfoMap.put("B 구역", b);
        }
        if(c !=null && c !=0){
            parkingInfoMap.put("C 구역", c);
        }
        if(d !=null && d !=0){
            parkingInfoMap.put("D 구역", d);
        }
    }

}