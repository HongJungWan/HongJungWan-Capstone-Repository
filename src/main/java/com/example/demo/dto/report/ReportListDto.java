package com.example.demo.dto.report;

import com.example.demo.domain.entity.Report;
import com.example.demo.domain.value.ReportStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportListDto {

    private Long id;

    private String user;

    private String college;

    private String carNumber;

    private String cause;

    private LocalDateTime reportDate;

    private ReportStatus status;

    public ReportListDto(Report report) {

        this.user = report.getUser().getName();
        this.college = report.getCollege().getCollegeName();
        this.carNumber = report.getCarNumber();
        this.cause = report.getCause();
        this.reportDate = report.getReportDate();
        this.status = report.getStatus();

    }
}
