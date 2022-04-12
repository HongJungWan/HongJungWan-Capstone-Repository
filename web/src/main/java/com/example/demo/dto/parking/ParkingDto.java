package com.example.demo.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingDto {

    private String parkingName;
    private Integer quantity;
}
