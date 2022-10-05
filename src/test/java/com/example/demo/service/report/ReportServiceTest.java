package com.example.demo.service.report;

import com.example.demo.domain.entity.Report;
import com.example.demo.repository.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReportServiceTest {

    @Autowired
    ReportService reportService;

    @Autowired
    ReportRepository reportRepository;


    @Test
    @DisplayName("신고 확인 (처리 필요 -> 처리 진행)")
    void reportControl() {

        // given
        Long report_id = Long.valueOf(3);

        // then
        Report report = reportRepository.findOne(report_id);
        report.control();

    }

    @Test
    @DisplayName("신고 목록 조회 (페이징)")
    void reportList() {

        // given
        int page = 0;

        // when
        Page<Report> reportList = reportService.reportList(page);

        // then
        assertThat(reportList.getTotalElements()).isEqualTo(13);
        assertThat(reportList.getTotalPages()).isEqualTo(3);

    }

}