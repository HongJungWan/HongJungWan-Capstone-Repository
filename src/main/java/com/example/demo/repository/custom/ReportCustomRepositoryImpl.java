package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Report;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ReportCustomRepositoryImpl implements ReportCustomRepository {

    private final EntityManager em;

    public ReportCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public Report findOne(Long id) {
        return em.find(Report.class, id);
    }
    
}
