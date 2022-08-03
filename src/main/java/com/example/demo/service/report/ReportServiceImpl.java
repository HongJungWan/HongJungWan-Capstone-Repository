package com.example.demo.service.report;

import com.example.demo.domain.entity.Report;
import com.example.demo.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public Page<Report> reportList(int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("reportDate"));
        Pageable pageable = PageRequest.of(page, 6, Sort.by(sorts));
        return reportRepository.findAll(pageable);
    }

}
