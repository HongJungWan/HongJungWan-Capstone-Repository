package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Report;

import java.util.Optional;

public interface ReportCustomRepository {

    Optional<Report> findReportDetail(Long id);
}
