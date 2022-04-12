//package com.example.demo.repository;
//
//
//import com.example.demo.domain.entity.AvailableDate;
//import com.example.demo.repository.custom.AvailableDateCustomRepository;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long>, AvailableDateCustomRepository {
//
//    /**
//     * dirtyChecking으로 수정핦 시, 너무 많은 쿼리를 쓰기 지연으로 남아 놓았기 때문에 벌크로 보내 주기위한 쿼리
//     * 혹시 모를 데이터 베이스와 영속성 컨텍스트 간 데이터 차이가 발생할 수 있으므로 자동으로 플러쉬
//     */
//    @Modifying
//    @Query( "update AvailableDate a " +
//            "set a.acceptCount= a.acceptCount+ :acceptCount, a.enabled=true " +
//            "where a.collage.id =:id")
//    void updateAvailableDateAcceptCount(@Param("acceptCount")Integer acceptCount,
//                                        @Param("id") Long id);
//
//    @Modifying
//    @Query("update AvailableDate a " +
//            "set a.acceptCount=0, a.enabled=false " +
//            "where a.id in (:ids)")
//    void updateAvailableDateAcceptCountToZero(@Param("ids")List<Long> ids);
//}
