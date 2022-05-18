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

    private String collegeName;

    private String parkingName;

    private ReserveStatus reserveStatus;

    //0509 수정
    private String phon_number;

    private String car_number;

    public ReserveWithUsernameDto(Reserve reserve) {
        reserveId = reserve.getReserve_id();
        username = reserve.getUser().getName();
        collegeName = reserve.getCollege().getCollegeName();
        parkingName = reserve.getParkingName();
        reserveStatus = reserve.getStatus();
        phon_number = reserve.getUser().getPhonNumber();
        car_number = reserve.getUser().getCar_number();
    }

}
