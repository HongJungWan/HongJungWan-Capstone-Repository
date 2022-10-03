package com.example.demo.service.admin;

import com.example.demo.domain.entity.College;
import com.example.demo.domain.entity.Reserve;
import com.example.demo.dto.college.CollegeListDto;
import com.example.demo.dto.college.CollegeResponseDto;
import com.example.demo.dto.college.CollegeSimpleInfoDto;
import com.example.demo.dto.college.CollegeUpdateDto;
import com.example.demo.repository.CollegeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Autowired
    CollegeRepository collegeRepository;

    @Test
    @DisplayName("주차장 이름으로 주차장 단건 조회")
    void getCollegeInfo() {

        // given
        String collegeName = "한울관 주차장";

        // when
        CollegeResponseDto getCollegeInfo = adminService.getCollegeInfo(collegeName);

        // then
        assertThat(getCollegeInfo).isEqualTo(null);

    }

    @Test
    @DisplayName("어드민이 관리하는 주차장 리스트를 보여주기 위한 메서드")
    void getAllSimpleCollegeInfo() {

        // given
        String name = "admin";

        // when
        List<CollegeSimpleInfoDto> getAllSimpleCollegeInfo = adminService.getAllSimpleCollegeInfo(name);

        // then
        assertThat(getAllSimpleCollegeInfo.size()).isEqualTo(15);

    }

    @Test
    @DisplayName("어드민이 관리하는 주차장 리스트를 보여주기 위한 메서드")
    void getCollegeList() {

        // given
        String name = "admin";
        String address = "경기 오산시 한신대길 138";
        int page = 0;

        // when
        Page<CollegeListDto> getCollegeList = adminService.getCollegeList(name, address, page);

        // then
        assertThat(getCollegeList.getTotalPages()).isEqualTo(1);
        assertThat(getCollegeList.getTotalElements()).isEqualTo(4);

    }

    @Test
    @DisplayName("주차장 상세 정보 조회 후 dto로 변환")
    void getCollege() {

        // given
        Long id = Long.valueOf(3);

        // when
        CollegeUpdateDto getCollege = adminService.getCollege(id);

        // then
        assertThat(getCollege.getCollegeName()).isEqualTo("한울관 주차장");

    }

    @Test
    @DisplayName("예약 현황 정보 얻어오기")
    void getReserveCondition() {

        // given
        int page = 0;
        Long collegeId = Long.valueOf(3);

        // when
        Page<Reserve> getReserveCondition = adminService.getReserveCondition(page, collegeId);

        // then
        assertThat(getReserveCondition.getTotalPages()).isEqualTo(3);
        assertThat(getReserveCondition.getTotalElements()).isEqualTo(17);

    }

    @Test
    @DisplayName("주차장 삭제(히든) 테스트")
    void cancelCollege() {

        // given
        Long collegeId = Long.valueOf(3);

        // when
        College college = collegeRepository.findOne(collegeId);
        college.hidden();

    }

}