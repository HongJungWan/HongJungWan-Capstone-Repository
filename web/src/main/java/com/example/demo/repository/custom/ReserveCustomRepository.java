package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Parking;
import com.example.demo.domain.entity.Reserve;

import java.util.List;

public interface ReserveCustomRepository {


    /**
     * 예약가능 주차장 구역이름 조회
     */
    List<Parking> findAvailableParkings(Long collegeId);

    /**
     * 예약 현황 조회
     */
    List<Reserve> findAllReserve(Long collegeId);

}
