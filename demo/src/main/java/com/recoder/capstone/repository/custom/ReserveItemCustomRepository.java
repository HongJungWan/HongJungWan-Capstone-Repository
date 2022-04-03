package com.recoder.capstone.repository.custom;

import com.recoder.capstone.domain.entity.AvailableDate;
import com.recoder.capstone.domain.entity.AvailableTime;
import com.recoder.capstone.domain.entity.Parking;
import com.recoder.capstone.domain.entity.ReserveItem;

import java.util.List;

public interface ReserveItemCustomRepository {

    /**
     * 예약가능 날짜 조회
     */
    List<AvailableDate> findAvailableDatesByCollegeId(Long id);

    /**
     * 예약가능 시간 조회
     */
    List<AvailableTime> findAvailableTimesByAvailableDateId(Long id);


    /**
     * 예약가능 주차장 구역이름 조회
     */
    List<Parking> findAvailableParkings(Long collegeId);

    /**
     * 예약 현황 조회
     */
    List<ReserveItem> findAllReserveItem(Long collegeId);


}
