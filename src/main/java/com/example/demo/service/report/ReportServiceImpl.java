package com.example.demo.service.report;

import com.example.demo.domain.entity.Report;
import com.example.demo.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public void reportControl(Long report_id) {

        Report report = reportRepository.findOne(report_id);

        report.control();
    }

}
