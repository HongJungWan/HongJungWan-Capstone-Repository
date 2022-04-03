package com.recoder.hong.repository.custom;

import com.recoder.hong.domain.entity.ParkingArea;

public interface ParkingAreaCustomRepository {

    ParkingArea findParking(Long parkingareaId, String parkingareaName);

    ParkingArea findParkingDisabled(Long parkingareaId, String parkingareaName);
}
