package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Parking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReserveCustomRepositoryImpl implements ReserveCustomRepository {

    private final EntityManager em;

    @Override
    public List<Parking> findAvailableParkings(Long collegeId) {
        return em.createQuery(
                        "select v " +
                                "from Parking v " +
                                "where v.college.id = :collegeId and v.quantity > 0 and v.enabled = true", Parking.class)
                .setParameter("collegeId", collegeId)
                .getResultList();
    }
    
}
