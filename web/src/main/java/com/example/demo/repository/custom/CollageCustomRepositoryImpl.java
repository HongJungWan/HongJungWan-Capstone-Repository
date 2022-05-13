package com.example.demo.repository.custom;


import com.example.demo.domain.entity.Collage;
import com.example.demo.dto.collage.CollageListDto;
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
public class CollageCustomRepositoryImpl implements CollageCustomRepository {

    private final EntityManager em;

    @Override
    public List<CollageListDto> findAllCollageInfo(Long id) {
        return em.createQuery(
                "select new com.example.demo.dto.collage.CollageListDto(h.id, h.collageName, h.address, h.totalQuantity, h.enabled) " +
                        "from Collage h " +
                        "where h.admin.id = :id"
                , CollageListDto.class).setParameter("id", id).getResultList();
    }

    @Override
    public Collage findOne(Long collageId) {
        return em.find(Collage.class, collageId);
    }

    /**
     * 주차장아이디로 주차장정보 조회
     */
    @Override
    public Optional<Collage> findCollageDetail(Long id) {
        return Optional.of(em.createQuery(
                "select distinct h from Collage h " +
                        "join fetch h.admin a " +
                        "join fetch h.parkings v " +
                        "where h.id= :id", Collage.class)
                .setParameter("id", id).getSingleResult());
    }

    /**
     * 예약가능 주차장 조회 + 페이징
     */
    @Override
    public List<CollageListDto> findCollageListPaging(int offset, int limit) {
        return em.createQuery(
                "select new com.example.demo.dto.collage.CollageListDto(h.id, h.collageName, h.address, h.totalQuantity) " +
                        "from Collage h " +
                        "where h.enabled = true", CollageListDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 주소로 예약가능 주차장 조회 + 페이징
     */
    @Override
    public List<CollageListDto> findCollageListByAddressPaging(int offset, int limit, @Param("address") String address) {
        return em.createQuery(
                "select new com.example.demo.dto.collage.CollageListDto(h.id, h.collageName, h.address, h.totalQuantity) " +
                        "from Collage h " +
                        "where h.enabled = true and h.address like '%'||:address||'%'", CollageListDto.class)
                .setParameter("address", address)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 주소, admin으로 주차장 조회
     */
    @Override
    public List<CollageListDto> findCollageListByAddressAndAdmin(@Param("address") String address, Long adminId) {
        return em.createQuery(
                "select new com.example.demo.dto.collage.CollageListDto(h.id, h.collageName, h.address, h.totalQuantity) " +
                        "from Collage h " +
                        "where h.admin.id= :adminId and h.address like '%'||:address||'%'", CollageListDto.class)
                .setParameter("address", address)
                .setParameter("adminId", adminId)
                .getResultList();
    }


}
