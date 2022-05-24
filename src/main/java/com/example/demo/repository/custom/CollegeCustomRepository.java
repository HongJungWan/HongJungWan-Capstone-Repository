package com.example.demo.repository.custom;


import com.example.demo.domain.entity.College;
import com.example.demo.dto.college.CollegeListDto;
import com.example.demo.dto.college.CollegeListUserDto;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollegeCustomRepository {

    College findOne(Long collegeId);

    List<CollegeListDto> findAllCollegeInfo(Long id);

    Optional<College> findCollegeDetail(Long id);

    List<CollegeListUserDto> findCollegeListPaging(int offset, int limit);

    List<CollegeListUserDto> findCollegeListByAddressPaging(int offset, int limit, @Param("address") String address);

    List<CollegeListDto> findCollegeListByAddressAndAdmin(@Param("address") String address, Long adminId);

}
