package com.example.demo.repository;

import com.example.demo.domain.entity.Report;
import com.example.demo.repository.custom.ReportCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long>, ReportCustomRepository {

    Page<Report> findAll(Pageable pageable);
}
