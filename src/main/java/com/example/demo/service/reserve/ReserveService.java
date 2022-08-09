package com.example.demo.service.reserve;

import com.example.demo.dto.college.CollegeListDto;
import com.example.demo.dto.parking.ParkingReserveDto;
import com.example.demo.dto.reserve.ReserveSimpleDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReserveService {
    
    Page<CollegeListDto> getCollegeList(String address, int page);

    List<ParkingReserveDto> getAvailableParkingNameList(Long collegeId);

    Long reserve(String username, Long collegeId, String parkingName);

    ReserveSimpleDto getReserveResult(String username);

    Integer validateDuplicateUser(String username);

    void cancelReserve(Long reserveId);

}
