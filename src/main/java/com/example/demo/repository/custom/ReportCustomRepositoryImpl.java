package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ReportCustomRepositoryImpl implements ReportCustomRepository {

    private final EntityManager em;

    /*
     * Report ID로 신고 조회
     * */
    @Override
    public Optional<Report> findReportDetail(Long id) {
        return Optional.of(em.createQuery(
                        "select distinct r from Report r " +
                                "join fetch r.user u " +
                                "join fetch r.college c " +
                                "where r.id = :id", Report.class)
                .setParameter("id", id).getSingleResult());
    }


}
