package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.ReportStatus;
import com.example.demo.domain.value.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReportTest {

    @Test
    @DisplayName("신고 엔티티 테스트")
    void saveTest() {

        College college = College.createCollege()
                .address("테스트 주소")
                .collegeName("테스트 주차장")
                .dateAccept(20)
                .detailAddress("테스트 상세주소")
                .build();

        User user = User.createUser()
                .id("1")
                .car_number("12가 1212")
                .name("홍")
                .password("1234")
                .gender(Gender.MALE)
                .age(25)
                .phonNumber("010-0000-0000")
                .email("test@naver.com")
                .role(Role.ROLE_USER)
                .build();

        Report report = Report.createReport()
                .carNumber("test가 123")
                .reportDate(LocalDateTime.now())
                .cause("원인")
                .user(user)
                .college(college)
                .status(ReportStatus.PROCESS)
                .build();

        assertThat(report.getCarNumber()).isEqualTo("test가 123");

    }
}