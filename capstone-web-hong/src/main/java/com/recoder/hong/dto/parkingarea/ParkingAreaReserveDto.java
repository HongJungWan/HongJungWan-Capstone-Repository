package com.recoder.hong.dto.parkingarea;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingAreaReserveDto {

    private String parkingareaName;
    private Integer quantity;
}
