package com.example.demo.domain.entity;

import com.example.demo.repository.CollegeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@WebAppConfiguration
class CollegeTest {

    @Autowired
    private CollegeRepository collegeRepository;

    @Test
    @DisplayName("대학 주차장 엔티티 테스트")
    void saveTest() {

        College college = College.createCollege()
                .address("")
                .collegeName("")
                .dateAccept(20)
                .detailAddress("")
                .build();

        College savedCollege = collegeRepository.save(college);

        Assertions.assertThat(college.getTotalQuantity()).isEqualTo(null);
    }
}