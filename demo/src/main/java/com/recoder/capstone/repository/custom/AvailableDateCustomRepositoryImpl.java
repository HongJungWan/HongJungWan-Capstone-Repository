package com.recoder.capstone.repository.custom;

import com.recoder.capstone.domain.entity.AvailableDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AvailableDateCustomRepositoryImpl implements AvailableDateCustomRepository{

    private final EntityManager em;

    @Override
    public AvailableDate findAvailableDateByCollegeIdAndDate(Long collegeId, String date) {
        return em.createQuery(
                "select d " +
                        "from AvailableDate d " +
                        "where d.college.id = :collegeId and d.date = :date", AvailableDate.class
        )
                .setParameter("collegeId", collegeId)
                .setParameter("date", date)
                .getSingleResult();

    }
}
