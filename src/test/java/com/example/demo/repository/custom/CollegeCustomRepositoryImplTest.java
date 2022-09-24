package com.example.demo.repository.custom;

import com.example.demo.domain.entity.College;
import com.example.demo.dto.college.CollegeListUserDto;
import com.example.demo.repository.CollegeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CollegeCustomRepositoryImplTest {

    @Autowired
    private CollegeRepository collegeRepository;

    @Test
    @DisplayName("주차장 조회")
    void findOne() {
        // given
        Long collegeId = Long.valueOf(3);

        // when
        College college = collegeRepository.findOne(collegeId);

        // then
        assertThat(college.getId()).isEqualTo(3);

    }

    @Test
    @DisplayName("주차장 아이디로 주차장 정보 조회")
    void findCollegeDetail() {
        // given
        Long Id = Long.valueOf(3);

        // when
        Optional<College> college = collegeRepository.findCollegeDetail(Id);

        // then
        assertThat(college.get().getCollegeName()).isEqualTo("한울관 주차장");
        assertThat(college.get().getDateAccept()).isEqualTo(20);
        assertThat(college.get().getTotalQuantity()).isEqualTo(120);
        assertThat(college.get().getId()).isEqualTo(3);

    }

    @Test
    @DisplayName("예약 가능 주차장 조회 + 레거시 페이징")
    void findCollegeListPaging() {
        // given
        int offset = 0;
        int limit = 10;

        // when
        List<CollegeListUserDto> college = collegeRepository.findCollegeListPaging(offset, limit);

        // then
        assertThat(college.size()).isEqualTo(10);

    }

    @ParameterizedTest
    @DisplayName("주소로 예약 가능 주차장 조회 + 페이징")
    @ValueSource(strings = {"경기 오산시 한신대길 137"})
    void findCollegeListByAddressPaging(String address) {
        // given
        int offset = 0;
        int limit = 10;

        // when
        List<CollegeListUserDto> college = collegeRepository.findCollegeListByAddressPaging(offset, limit, address);

        // then
        assertThat(college.size()).isEqualTo(5);

    }

}