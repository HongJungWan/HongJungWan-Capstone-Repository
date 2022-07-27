package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.ReportStatus;
import com.example.demo.domain.value.Role;
import com.example.demo.repository.ReportRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@WebAppConfiguration
class ReportTest {

    @Autowired
    private ReportRepository reportRepository;

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
                .status(ReportStatus.PROCESS)
                .build();

        Report savedReport = reportRepository.save(report);

        Assertions.assertThat(report.getCarNumber()).isEqualTo("test가 123");

    }
}