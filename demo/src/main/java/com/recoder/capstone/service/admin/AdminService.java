package com.recoder.capstone.service.admin;

import com.recoder.capstone.dto.college.*;
import com.recoder.capstone.dto.reserve.ReserveItemWithUsernameDto;

import java.text.ParseException;
import java.util.List;

public interface AdminService {

    /**
     * 주차장등록
     */
    Long addCollege(CollegeRequestDto collegeAddDto, String adminName) throws Exception;


    /**
     * 주차장이름으로 주차장 단건 조회
     */
    CollegeResponseDto getCollegeInfo(String collegeName);

    /**
     * 어드민이 관리하는 주차장 리스트를 보여주기 위한 메서드
     */
    List<CollegeSimpleInfoDto> getAllSimpleCollegeInfo(String name);

    List<CollegeListDto> getCollegeList(String name, String address);

    /**
     * 주차장 상세 정보 조회 후 dto로 변환
     */
    CollegeUpdateDto getCollege(Long id);

    /**
     * 주차장 update
     */
    Long collegeUpdate(CollegeUpdateDto dto) throws ParseException;

    /**
     * 예약 현황 정보
     */
    List<ReserveItemWithUsernameDto> getReserveItemCondition(Long collegeId);
}