package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Parking;
import com.example.demo.domain.entity.Reserve;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReserveCustomRepositoryImpl implements ReserveCustomRepository {

    private final EntityManager em;

    @Override
    public List<Parking> findAvailableParkings(Long collageId) {
        return em.createQuery(
                "select v " +
                        "from Parking v " +
                        "where v.collage.id = :collageId and v.quantity > 0 and v.enabled = true", Parking.class
        )
                .setParameter("collageId", collageId)
                .getResultList();
    }

    //0508 애 건들여야 할듯
    @Override
    public List<Reserve> findAllReserve(Long collageId) {
        return em.createQuery(
                "select distinct ri " +
                        "from Reserve ri " +
                        "join fetch ri.user u " +
                        "where ri.Collage.id = :collageId"
                , Reserve.class)
                .setParameter("collageId", collageId)
                .getResultList();
    }
}
