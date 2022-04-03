package com.recoder.capstone.dto.reserve;

import com.recoder.capstone.domain.entity.ReserveItem;
import com.recoder.capstone.domain.value.ReserveStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReserveItemWithUsernameDto {

    private Long reserveItemId;

    private String username;

    private String collegeName;

    private String parkingName;

    private String reserveDate;

    private Integer reserveTime;

    private ReserveStatus reserveStatus;

    public ReserveItemWithUsernameDto(ReserveItem reserveItem){
        this.reserveItemId = reserveItem.getId();
        this.username = reserveItem.getUser().getName();
        this.collegeName = reserveItem.getCollege().getCollegeName();
        this.parkingName = reserveItem.getParkingName();
        this.reserveDate = reserveItem.getReserveDate();
        this.reserveTime = reserveItem.getReserveTime();
        this.reserveStatus = reserveItem.getStatus();
    }

}
