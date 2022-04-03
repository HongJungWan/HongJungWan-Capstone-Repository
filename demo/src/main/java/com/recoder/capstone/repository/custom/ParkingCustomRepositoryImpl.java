package com.recoder.capstone.repository.custom;

import com.recoder.capstone.domain.entity.Parking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ParkingCustomRepositoryImpl implements ParkingCustomRepository {

    private final EntityManager em;

    @Override
    public Parking findParking(Long collegeId, String parkingName) {
        return em.createQuery(
                "select v " +
                        "from Parking v  join v.college h " +
                        "where h.id = :collegeId and v.parkingName = :parkingName and v.enabled = true", Parking.class
        )
                .setParameter("collegeId", collegeId)
                .setParameter("parkingName", parkingName)
                .getSingleResult();
    }

    @Override
    public Parking findParkingDisabled(Long collegeId, String parkingName) {
        return em.createQuery(
                "select v " +
                        "from Parking v  join v.college h " +
                        "where h.id = :collegeId and v.parkingName = :parkingName", Parking.class
        )
                .setParameter("collegeId", collegeId)
                .setParameter("parkingName", parkingName)
                .getSingleResult();
    }
}
