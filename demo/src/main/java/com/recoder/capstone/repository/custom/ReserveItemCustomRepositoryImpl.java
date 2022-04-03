package com.recoder.capstone.repository.custom;

import com.recoder.capstone.domain.entity.AvailableDate;
import com.recoder.capstone.domain.entity.AvailableTime;
import com.recoder.capstone.domain.entity.Parking;
import com.recoder.capstone.domain.entity.ReserveItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReserveItemCustomRepositoryImpl implements ReserveItemCustomRepository {

    private final EntityManager em;

    @Override
    public List<AvailableDate> findAvailableDatesByCollegeId(Long id) {
        return em.createQuery(
                "select d " +
                        "from AvailableDate d " +
                        "where d.college.id = :id and d.enabled = true", AvailableDate.class
        )
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<AvailableTime> findAvailableTimesByAvailableDateId(Long id) {
        return em.createQuery(
                "select t " +
                        "from AvailableTime  t " +
                        "where t.availableDate.id = :id and t.enabled = true" , AvailableTime.class
        )
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Parking> findAvailableParkings(Long collegeId) {
        return em.createQuery(
                "select v " +
                        "from Parking v " +
                        "where v.college.id = :collegeId and v.quantity > 0 and v.enabled = true", Parking.class
        )
                .setParameter("collegeId", collegeId)
                .getResultList();
    }

    @Override
    public List<ReserveItem> findAllReserveItem(Long collegeId){
        return em.createQuery(
                "select distinct ri " +
                        "from ReserveItem ri " +
                        "join fetch ri.user u " +
                        "where ri.College.id = :collegeId"
        ,ReserveItem.class)
                .setParameter("collegeId",collegeId)
                .getResultList();
    }
}
