package com.example.demo.controller;


import com.example.demo.dto.collage.CollageListDto;
import com.example.demo.dto.collage.CollageResponseDto;
import com.example.demo.dto.collage.CollageSimpleInfoDto;
import com.example.demo.dto.security.PrincipalDetails;
import com.example.demo.service.admin.AdminService;
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
    @GetMapping("/collage")
    @ResponseBody
    public ResponseEntity<CollageResponseDto> getCollage(@RequestParam("name") String collageName) {
        CollageResponseDto collageResponseDto = adminService.getCollageInfo(collageName);

        return ResponseEntity.ok(collageResponseDto);
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
     * 주차장 목록
     */
    @GetMapping("/collage/list")
    public String collageList(@AuthenticationPrincipal PrincipalDetails principal, Model model,
                               @RequestParam(defaultValue = "noSearch")String addressSearch) {
        String adminName = principal.getName();
        List<CollageListDto> collageList = adminService.getCollageList(adminName,addressSearch);
        model.addAttribute("collageList", collageList);
        return "admin/collageList";
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