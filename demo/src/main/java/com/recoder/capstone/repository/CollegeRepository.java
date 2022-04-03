package com.recoder.capstone.repository;

import com.recoder.capstone.domain.entity.Admin;
import com.recoder.capstone.domain.entity.College;
import com.recoder.capstone.dto.college.CollegeSimpleInfoDto;
import com.recoder.capstone.repository.custom.CollegeCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, Long>, CollegeCustomRepository {

    Optional<College> findByCollegeName(String collegeName);

    /**
     *  CollegeSimpleInfoDto 를 이용한 모든 주차장의 이름, 주소 조회
     *  @return CollegeSimpleInfoDto
     */
    @Query("select new com.recoder.capstone.dto.college.CollegeSimpleInfoDto(h.collegeName,h.address) " +
            "from College h " +
            "where h.enabled = true")
    List<CollegeSimpleInfoDto> findAllCollegeNameAndAddress();

    /**
     * 어드민이 관리하는 모든 주차장 정보 조회 (주차장이름, 장소)
     * 어드민이 등록한 모든 주차장의 간단한 정보만을 조회하기 위한 쿼리
     */
    @Query("select new com.recoder.capstone.dto.college.CollegeSimpleInfoDto(h.collegeName, h.address) " +
            "from College h " +
            "where h.admin = :admin")
    List<CollegeSimpleInfoDto> findAllByAdmin(Admin admin);
}
