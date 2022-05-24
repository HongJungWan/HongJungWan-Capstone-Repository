package com.example.demo.service.reserve;

import com.example.demo.dto.Parking.ParkingReserveDto;
import com.example.demo.dto.college.CollegeListUserDto;
import com.example.demo.dto.reserve.ReserveSimpleDto;

import java.util.List;

public interface ReserveService {

    List<CollegeListUserDto> getAllCollegeInfo(int offset, int limit);

    List<CollegeListUserDto> getAllCollegeInfoSearchByAddress(String address, int offset, int limit);

    List<ParkingReserveDto> getAvailableParkingNameList(Long collegeId);

    Long reserve(String username, Long collegeId, String parkingName);

    ReserveSimpleDto getReserveResult(String username);

    void validateDuplicateUser(String username);

    void cancelReserve(Long reserveId);

}
