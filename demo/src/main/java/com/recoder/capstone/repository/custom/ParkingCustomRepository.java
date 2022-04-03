package com.recoder.capstone.repository.custom;

import com.recoder.capstone.domain.entity.Parking;

public interface ParkingCustomRepository {

    Parking findParking(Long collegeId, String parkingName);

    Parking findParkingDisabled(Long collegeId, String parkingName);
}
