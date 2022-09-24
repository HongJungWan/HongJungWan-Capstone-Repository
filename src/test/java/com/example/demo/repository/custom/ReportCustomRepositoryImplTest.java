package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Report;
import com.example.demo.repository.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.domain.value.ReportStatus.PROCESS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReportCustomRepositoryImplTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    @DisplayName("신고 조회")
    void findOne() {
        // given
        Long id = Long.valueOf(3);

        // when
        Report report = reportRepository.findOne(id);

        // then
        assertThat(report.getId()).isEqualTo(3);
        assertThat(report.getCarNumber()).isEqualTo("12가 3456");
        assertThat(report.getCause()).isEqualTo("불법주차");
        assertThat(report.getStatus()).isEqualTo(PROCESS);
    }

}