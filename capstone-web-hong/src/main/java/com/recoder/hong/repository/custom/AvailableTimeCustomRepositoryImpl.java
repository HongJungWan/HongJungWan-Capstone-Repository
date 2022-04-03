package com.recoder.hong.repository.custom;

import com.recoder.hong.domain.entity.AvailableTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AvailableTimeCustomRepositoryImpl implements AvailableTimeCustomRepository{

    private final EntityManager em;

    @Override
    public AvailableTime findAvailableTimeById(Long timeId) {
        return em.createQuery(
                "select t " +
                        "from AvailableTime t " +
                        "where t.id = :timeId and t.enabled=true", AvailableTime.class
        )
                .setParameter("timeId", timeId)
                .getSingleResult();
    }

    @Override
    public AvailableTime findAvailableTimeByTimeAndDateId(Integer time, Long dateId) {
        return em.createQuery(
                "select t " +
                        "from AvailableTime t " +
                        "where t.time = :time and t.availableDate.id = :dateId", AvailableTime.class
        )
                .setParameter("time", time)
                .setParameter("dateId", dateId)
                .getSingleResult();
    }


}
