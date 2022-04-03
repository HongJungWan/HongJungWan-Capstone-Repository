package com.recoder.hong.repository.custom;

import com.recoder.hong.domain.entity.ParkingArea;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ParkingAreaCustomRepositoryImpl implements ParkingAreaCustomRepository{

    private final EntityManager em;


    @Override
    public ParkingArea findParking(Long collegeId, String parkingareaName) {
        return em.createQuery(
                "select v " +
                        "from ParkingArea v  join v.college h " +
                        "where h.id = :collegeId and v.parkingareaName = :parkingareaName and v.enabled = true", ParkingArea.class
        )
                .setParameter("collegeId", collegeId)
                .setParameter("parkingareaName", parkingareaName)
                .getSingleResult();
    }

    @Override
    public ParkingArea findParkingDisabled(Long collegeId, String parkingareaName) {
        return em.createQuery(
                "select v " +
                        "from ParkingArea v  join v.college h " +
                        "where h.id = :collegeId and v.parkingareaName = :parkingareaName", ParkingArea.class
        )
                .setParameter("collegeId", collegeId)
                .setParameter("parkingareaName", parkingareaName)
                .getSingleResult();
    }
}
