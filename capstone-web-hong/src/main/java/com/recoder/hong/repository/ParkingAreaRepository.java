package com.recoder.hong.repository;

import com.recoder.hong.domain.entity.ParkingArea;
import com.recoder.hong.repository.custom.ParkingAreaCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingAreaRepository extends JpaRepository<ParkingArea, Long>, ParkingAreaCustomRepository {
}
