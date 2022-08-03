package com.example.demo.service.report;

import com.example.demo.domain.entity.Report;
import org.springframework.data.domain.Page;

public interface ReportService {

    void reportControl(Long report_id);

    Page<Report> reportList(int page);
}
