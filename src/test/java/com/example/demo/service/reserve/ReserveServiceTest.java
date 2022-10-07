package com.example.demo.service.reserve;

import com.example.demo.dto.college.CollegeListDto;
import com.example.demo.dto.parking.ParkingReserveDto;
import com.example.demo.dto.reserve.ReserveSimpleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
class ReserveServiceTest {

    @Autowired
    private ReserveService reserveService;

    @Test
    @DisplayName("모든 주차장 목록 (페이징)")
    void getCollegeList() {

        // given
        String address = "경기 오산시 한신대길 137";
        int page = 0;

        // when
        Page<CollegeListDto> getCollegeList = reserveService.getCollegeList(address, page);

        // then
        assertThat(getCollegeList.getTotalPages()).isEqualTo(1);
        assertThat(getCollegeList.getTotalElements()).isEqualTo(5);
    }

    @Test
    @DisplayName("예약 가능 주차 구역 조회")
    void getAvailableParkingNameList() {

        // given
        Long collegeId = Long.valueOf(3);

        // when
        List<ParkingReserveDto> getAvailableParkingNameList = reserveService.getAvailableParkingNameList(collegeId);

        // then
        assertThat(getAvailableParkingNameList.size()).isEqualTo(4);

    }

    @Test
    @DisplayName("예약, 수정 필요한 테스트 코드")
    void reserve() {

        // given
        String username = "ghdwjddhks9898@naver.com";
        Long collegeId = Long.valueOf(3);
        String parkingName = "전기차 전용 구역";

        // when
        Long reserve = reserveService.reserve(username, collegeId, parkingName);

        // then
        assertAll(
                () -> assertFalse(reserve.toString().equals(""))
        );

    }

    @Test
    @DisplayName("예약 결과")
    void getReserveResult() {

        // given
        String username = "ghdwjddhks9898@naver.com";

        // when
        ReserveSimpleDto getReserveResult = reserveService.getReserveResult(username);

        // then
        assertThat(getReserveResult.getReserveId()).isEqualTo(2);
        assertThat(getReserveResult.getCollegeName()).isEqualTo("한울관 주차장");

    }

}