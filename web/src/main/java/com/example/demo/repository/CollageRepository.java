package com.example.demo.repository;


import com.example.demo.domain.entity.Admin;
import com.example.demo.domain.entity.Collage;
import com.example.demo.dto.collage.CollageSimpleInfoDto;
import com.example.demo.repository.custom.CollageCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollageRepository extends JpaRepository<Collage, Long>, CollageCustomRepository {

    Optional<Collage> findByCollageName(String collageName);

    /**
     *  CollageSimpleInfoDto 를 이용한 모든 주차장의 이름, 주소 조회
     *  @return CollageSimpleInfoDto
     */
    @Query("select new com.example.demo.dto.collage.CollageSimpleInfoDto(h.collageName,h.address) " +
            "from Collage h " +
            "where h.enabled = true")
    List<CollageSimpleInfoDto> findAllCollageNameAndAddress();

    /**
     * 어드민이 관리하는 모든 주차장 정보 조회 (주차장이름, 장소)
     * 어드민이 등록한 모든 주차장의 간단한 정보만을 조회하기 위한 쿼리
     */
    @Query("select new com.example.demo.dto.collage.CollageSimpleInfoDto(h.collageName, h.address) " +
            "from Collage h " +
            "where h.admin = :admin")
    List<CollageSimpleInfoDto> findAllByAdmin(Admin admin);
}
