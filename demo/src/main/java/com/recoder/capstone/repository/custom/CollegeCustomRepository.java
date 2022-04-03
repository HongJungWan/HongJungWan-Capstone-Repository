package com.recoder.capstone.repository.custom;

import com.recoder.capstone.domain.entity.College;
import com.recoder.capstone.dto.college.CollegeListDto;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollegeCustomRepository {

    List<CollegeListDto> findAllCollegeInfo(Long id);

    Optional<College> findCollegeDetail(Long id);

    List<CollegeListDto> findCollegeListPaging(int offset, int limit);

    List<CollegeListDto> findCollegeListByAddressPaging(int offset, int limit, @Param("address") String address);

    List<CollegeListDto> findCollegeListByAddressAndAdmin(@Param("address") String address, Long adminId);

}
