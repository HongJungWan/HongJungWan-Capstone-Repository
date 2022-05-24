package com.example.demo.repository;


import com.example.demo.domain.entity.Reserve;
import com.example.demo.dto.reserve.ReserveSimpleDto;
import com.example.demo.repository.custom.ReserveCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Long>, ReserveCustomRepository {

    //0508 수정, 이 Query 안쓰는데 혹시 모르니까 냅두기, 나중에 써먹게
    @Query("select new com.example.demo.dto.reserve.ReserveSimpleDto(ri.reserve_id, ri.College.collegeName, ri.parkingName, ri.status) " +
            "from Reserve ri " +
            "where ri.user.user_id = :userId")
    Optional<ReserveSimpleDto> findByUserId(Long userId);
}
