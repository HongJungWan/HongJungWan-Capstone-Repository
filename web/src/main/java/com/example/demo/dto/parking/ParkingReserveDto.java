package com.example.demo.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingReserveDto {

    private Long id;
    private String parkingName;
}
