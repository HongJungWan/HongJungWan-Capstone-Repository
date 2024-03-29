package com.example.demo.repository.custom;


import com.example.demo.domain.entity.College;
import com.example.demo.dto.college.CollegeListUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CollegeCustomRepositoryImpl implements CollegeCustomRepository {

    private final EntityManager em;

    @Override
    public College findOne(Long collegeId) {
        return em.find(College.class, collegeId);
    }

    /**
     * 주차장아이디로 주차장정보 조회
     */
    @Override
    public Optional<College> findCollegeDetail(Long id) {
        return Optional.of(em.createQuery(
                        "select distinct h from College h " +
                                "join fetch h.admin a " +
                                "join fetch h.parkings v " +
                                "where h.id= :id", College.class)
                .setParameter("id", id).getSingleResult());
    }

    /**
     * 예약가능 주차장 조회 + 레거시 페이징
     */
    @Override
    public List<CollegeListUserDto> findCollegeListPaging(int offset, int limit) {
        return em.createQuery(
                        "select new com.example.demo.dto.college.CollegeListUserDto(h.id, h.collegeName, h.address, h.totalQuantity) " +
                                "from College h " +
                                "where h.enabled = true", CollegeListUserDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 주소로 예약가능 주차장 조회 + 페이징
     */
    @Override
    public List<CollegeListUserDto> findCollegeListByAddressPaging(int offset, int limit, @Param("address") String address) {
        return em.createQuery(
                        "select new com.example.demo.dto.college.CollegeListUserDto(h.id, h.collegeName, h.address, h.totalQuantity) " +
                                "from College h " +
                                "where h.enabled = true and h.address like '%'||:address||'%'", CollegeListUserDto.class)
                .setParameter("address", address)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


}
