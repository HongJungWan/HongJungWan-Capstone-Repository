package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Report;

public interface ReportCustomRepository {

    Report findOne(Long id);
}
