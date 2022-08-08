package com.example.demo.repository;


import com.example.demo.domain.entity.Admin;
import com.example.demo.domain.entity.College;
import com.example.demo.dto.college.CollegeListDto;
import com.example.demo.dto.college.CollegeSimpleInfoDto;
import com.example.demo.repository.custom.CollegeCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, Long>, CollegeCustomRepository {

    Optional<College> findByCollegeName(String collegeName);

    /**
     * CollegeSimpleInfoDto 를 이용한 모든 주차장의 이름, 주소 조회
     *
     * @return CollegeSimpleInfoDto
     */
    @Query("select new com.example.demo.dto.college.CollegeSimpleInfoDto(h.collegeName,h.address) " +
            "from College h " +
            "where h.enabled = true")
    List<CollegeSimpleInfoDto> findAllCollegeNameAndAddress();

    /**
     * 어드민이 관리하는 모든 주차장 정보 조회 (주차장이름, 장소)
     * 어드민이 등록한 모든 주차장의 간단한 정보만을 조회하기 위한 쿼리
     */
    @Query("select new com.example.demo.dto.college.CollegeSimpleInfoDto(h.collegeName, h.address) " +
            "from College h " +
            "where h.admin = :admin")
    List<CollegeSimpleInfoDto> findAllByAdmin(Admin admin);

    /**
     * 어드민이 관리하는 주차장 리스트 - 검색 기능
     */
    @Query("select new com.example.demo.dto.college.CollegeListDto(h.id, h.collegeName, h.address, h.totalQuantity, h.enabled) " +
            "from College h " +
            "where h.admin.id= :adminId and h.address like '%'||:address||'%'")
    Page<CollegeListDto> findCollegeListByAddressAndAdmin(Pageable pageable, @Param("address") String address, Long adminId);


    /**
     * 어드민이 관리하는 모든 주차장 리스트
     */
    @Query("select new com.example.demo.dto.college.CollegeListDto(h.id, h.collegeName, h.address, h.totalQuantity, h.enabled) " +
            "from College h " +
            "where h.admin.id = :id")
    Page<CollegeListDto> findAllCollegeInfo(Pageable pageable, Long id);

}
