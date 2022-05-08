package com.example.demo.dto.reserve;

import com.example.demo.domain.entity.Reserve;
import com.example.demo.domain.value.ReserveStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReserveWithUsernameDto {

    private Long reserveId;

    private String username;

    private String collageName;

    private String parkingName;

    private ReserveStatus reserveStatus;

    public ReserveWithUsernameDto(Reserve reserve) {
        reserveId = reserve.getReserve_id();
        username = reserve.getUser().getName();
        collageName = reserve.getCollage().getCollageName();
        parkingName = reserve.getParkingName();
        reserveStatus = reserve.getStatus();
    }

}
