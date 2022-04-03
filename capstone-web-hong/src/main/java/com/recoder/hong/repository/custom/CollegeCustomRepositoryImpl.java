package com.recoder.hong.repository.custom;


import com.recoder.hong.domain.entity.College;
import com.recoder.hong.dto.college.CollegeListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CollegeCustomRepositoryImpl implements CollegeCustomRepository{

    private final EntityManager em;

    @Override
    public List<CollegeListDto> findAllCollegeInfo(Long id) {
        return em.createQuery(
                "select new com.recoder.hong.dto.college.CollegeListDto(h.id, h.collegeName, h.address, h.totalQuantity) " +
                        "from College h " +
                        "where h.admin.id = :id"
                , CollegeListDto.class).setParameter("id",id).getResultList();
    }

    @Override
    public Optional<College> findCollegeDetail(Long id) {
        return Optional.of(em.createQuery(
                "select distinct h from College h " +
                        "join fetch h.admin a " +
                        "join fetch h.parkingareas v " +
                        "where h.id= :id",College.class)
                .setParameter("id",id).getSingleResult());
    }

    @Override
    public List<CollegeListDto> findCollegeListPaging(int offset, int limit) {
        return em.createQuery(
                "select new com.recoder.hong.dto.college.CollegeListDto(h.id, h.collegeName, h.address, h.totalQuantity) " +
                        "from College h " +
                        "where h.enabled = true", CollegeListDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<CollegeListDto> findCollegeListByAddressPaging(int offset, int limit, String address) {
        return em.createQuery(
                "select new com.recoder.hong.dto.college.CollegeListDto(h.id, h.collegeName, h.address, h.totalQuantity) " +
                        "from College h " +
                        "where h.enabled = true and h.address like '%'||:address||'%'", CollegeListDto.class)
                .setParameter("address", address)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<CollegeListDto> findCollegeListByAddressAndAdmin(String address, Long adminId) {
        return em.createQuery(
                "select new com.recoder.hong.dto.college.CollegeListDto(h.id, h.collegeName, h.address, h.totalQuantity) " +
                        "from College h " +
                        "where h.admin.id= :adminId and h.address like '%'||:address||'%'",CollegeListDto.class)
                .setParameter("address", address)
                .setParameter("adminId",adminId)
                .getResultList();
    }
}
