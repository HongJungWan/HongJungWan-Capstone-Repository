//package com.example.demo.repository.custom;
//
//import com.example.demo.domain.entity.AvailableDate;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//
//@Repository
//@RequiredArgsConstructor
//public class AvailableDateCustomRepositoryImpl implements AvailableDateCustomRepository{
//
//    private final EntityManager em;
//
//    @Override
//    public AvailableDate findAvailableDateByCollageIdAndDate(Long collageId, String date) {
//        return em.createQuery(
//                "select d " +
//                        "from AvailableDate d " +
//                        "where d.collage.id = :collageId and d.date = :date", AvailableDate.class
//        )
//                .setParameter("collageId", collageId)
//                .setParameter("date", date)
//                .getSingleResult();
//
//    }
//}
