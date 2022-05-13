package com.example.demo.repository.custom;


import com.example.demo.domain.entity.Collage;
import com.example.demo.dto.collage.CollageListDto;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollageCustomRepository {

    Collage findOne(Long collageId);

    List<CollageListDto> findAllCollageInfo(Long id);

    Optional<Collage> findCollageDetail(Long id);

    List<CollageListDto> findCollageListPaging(int offset, int limit);

    List<CollageListDto> findCollageListByAddressPaging(int offset, int limit, @Param("address") String address);

    List<CollageListDto> findCollageListByAddressAndAdmin(@Param("address") String address, Long adminId);

}
