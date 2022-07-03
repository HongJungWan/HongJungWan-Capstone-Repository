package com.example.demo.repository;

import com.example.demo.dto.college.CollegeSimpleInfoDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
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

        // 현재 로컬 DB에는 8개 등록되어있음
        Assertions.assertThat(list.size()).isEqualTo(8);
    }

}