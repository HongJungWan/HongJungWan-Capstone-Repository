package com.example.demo.service.admin;

import com.example.demo.dto.collage.CollageListDto;
import com.example.demo.dto.collage.CollageRequestDto;
import com.example.demo.dto.collage.CollageResponseDto;
import com.example.demo.dto.collage.CollageSimpleInfoDto;

import java.text.ParseException;
import java.util.List;

public interface AdminService {


    /**
     * 주차장등록
     */
    Long addCollage(CollageRequestDto collageAddDto, String adminName) throws Exception;

    /**
     * 주차장이름으로 주차장 단건 조회
     */
    CollageResponseDto getCollageInfo(String collageName);

    /**
     * 어드민이 관리하는 주차장 리스트를 보여주기 위한 메서드
     */
    List<CollageSimpleInfoDto> getAllSimpleCollageInfo(String name);

    List<CollageListDto> getCollageList(String name, String address);


}