package com.example.demo.repository;


import com.example.demo.domain.entity.Reserve;
import com.example.demo.dto.college.CollegeListDto;
import com.example.demo.dto.reserve.ReserveSimpleDto;
import com.example.demo.repository.custom.ReserveCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Long>, ReserveCustomRepository {

    @Query("select new com.example.demo.dto.reserve.ReserveSimpleDto(ri.reserve_id, ri.College.collegeName, ri.parkingName, ri.status) " +
            "from Reserve ri " +
            "where ri.user.user_id = :userId")
    Optional<ReserveSimpleDto> findByUserId(Long userId);

    /**
     * 어드민이 관리하는 주차장 리스트 - 검색 기능
     */
    @Query("select new com.example.demo.dto.college.CollegeListDto(h.id, h.collegeName, h.address, h.totalQuantity, h.enabled) " +
            "from College h " +
            "where h.address like '%'||:address||'%'")
    Page<CollegeListDto> findCollegeListByAddressAndAdmin(Pageable pageable, @Param("address") String address);


    /**
     * 모든 주차장 리스트
     */
    @Query("select new com.example.demo.dto.college.CollegeListDto(h.id, h.collegeName, h.address, h.totalQuantity, h.enabled) " +
            "from College h ")
    Page<CollegeListDto> findAllCollegeInfo(Pageable pageable);
}
