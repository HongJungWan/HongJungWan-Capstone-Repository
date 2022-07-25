package com.example.demo.dto.report;

import com.example.demo.domain.value.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ReportListDto {

    private Long id;

    private String user;

    private String college;

    private String carNumber;

    private String cause;

    private LocalDateTime reportDate;

    private ReportStatus status;

}
