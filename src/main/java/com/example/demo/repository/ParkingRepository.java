package com.example.demo.repository;

import com.example.demo.domain.entity.Parking;
import com.example.demo.repository.custom.ParkingCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Long>, ParkingCustomRepository {
}
