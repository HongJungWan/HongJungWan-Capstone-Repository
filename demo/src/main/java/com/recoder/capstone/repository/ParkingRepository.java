package com.recoder.capstone.repository;

import com.recoder.capstone.domain.entity.Parking;
import com.recoder.capstone.repository.custom.ParkingCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Long>, ParkingCustomRepository {

}
