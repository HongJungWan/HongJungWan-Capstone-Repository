package com.example.demo.repository;

import com.example.demo.domain.entity.College;
import com.example.demo.domain.entity.Parking;
import com.example.demo.dto.college.CollegeSimpleInfoDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class CollegeRepositoryTest {

    @Autowired
    private CollegeRepository collegeRepository;

    @Test
    @DisplayName("모든 주차장의 이름, 주소 조회 쿼리 테스트")
    void 주차장_이름_주소_조회() {
        List<CollegeSimpleInfoDto> list = collegeRepository.findAllCollegeNameAndAddress();

        for (CollegeSimpleInfoDto dto : list) {
            System.out.println("dto.getCollegeName() = " + dto.getCollegeName());
            System.out.println("dto.getAddress() = " + dto.getAddress());
        }

        // 현재 RDS에는 4개 등록되어있음
        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("주차장 이름으로 조회 테스트")
    void 주차장이름_조회() {
        College college = collegeRepository.findByCollegeName("DB 마이그레이션 TEST")
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("미등록 주차장");
                });

        String collegeName = college.getCollegeName();
        List<Parking> parkings = college.getParkings();

        System.out.println("parkings.size() = " + parkings.size());
    }

}