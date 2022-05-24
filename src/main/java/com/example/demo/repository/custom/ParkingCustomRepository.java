package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Parking;

public interface ParkingCustomRepository {

    Parking findParking(Long collegeId, String parkingName);

    Parking findParkingDisabled(Long collegeId, String parkingName);
}
