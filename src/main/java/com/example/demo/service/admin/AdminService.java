package com.example.demo.service.admin;

import com.example.demo.domain.entity.Reserve;
import com.example.demo.dto.college.*;
import org.springframework.data.domain.Page;

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

    Page<CollegeListDto> getCollegeList(String name, String address, int page);

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
    Page<Reserve> getReserveCondition(int page, Long collegeId);

    /**
     * 주차장 등록 취소(hidden)
     * */
    void cancelCollege(Long collegeId);

}