package com.recoder.capstone.repository;

import com.recoder.capstone.domain.entity.ReserveItem;
import com.recoder.capstone.dto.reserve.ReserveItemSimpleDto;
import com.recoder.capstone.repository.custom.ReserveItemCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReserveItemRepository extends JpaRepository<ReserveItem, Long>, ReserveItemCustomRepository {

    @Query("select new com.recoder.capstone.dto.reserve.ReserveItemSimpleDto(ri.id, ri.College.collegeName, ri.parkingName, ri.reserveDate, ri.reserveTime, ri.status) " +
            "from ReserveItem  ri " +
            "where ri.user.id = :userId")
    Optional<ReserveItemSimpleDto> findByUserId(Long userId);
}
