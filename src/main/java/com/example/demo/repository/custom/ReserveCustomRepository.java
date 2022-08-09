package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Parking;

import java.util.List;

public interface ReserveCustomRepository {
    
    /**
     * 예약가능 주차장 구역이름 조회
     */
    List<Parking> findAvailableParkings(Long collegeId);

}
