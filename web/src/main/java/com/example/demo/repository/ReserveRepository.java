package com.example.demo.repository;


import com.example.demo.domain.entity.Reserve;
import com.example.demo.dto.reserve.ReserveSimpleDto;
import com.example.demo.repository.custom.ReserveCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Long>, ReserveCustomRepository {

    @Query("select new com.example.demo.dto.reserve.ReserveSimpleDto(ri.id, ri.Collage.collageName, ri.parkingName, ri.status) " +
            "from Reserve ri " +
            "where ri.user.id = :userId")
    Optional<ReserveSimpleDto> findByUserId(Long userId);
}
