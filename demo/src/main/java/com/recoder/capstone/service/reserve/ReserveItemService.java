package com.recoder.capstone.service.reserve;

import com.recoder.capstone.dto.college.CollegeListDto;
import com.recoder.capstone.dto.parking.ParkingReserveDto;
import com.recoder.capstone.dto.reserve.AvailableDateDto;
import com.recoder.capstone.dto.reserve.AvailableTimeDto;
import com.recoder.capstone.dto.reserve.ReserveItemSimpleDto;

import java.util.List;

public interface ReserveItemService {

    List<CollegeListDto> getAllCollegeInfo(int offset, int limit);

    List<CollegeListDto> getAllCollegeInfoSearchByAddress(String address, int offset, int limit);

    List<AvailableDateDto> getAvailableDates(Long collegeId);

    List<AvailableTimeDto> getAvailableTimes(Long id);

    List<ParkingReserveDto> getAvailableParkingNameList(Long collegeId);

    Long reserve(String username, Long collegeId, String parkingName, Long dateId, Long timeId);

    ReserveItemSimpleDto getReserveResult(String username);

    void validateDuplicateUser(String username);

    void cancelReserveItem(Long reserveItemId);
}
