package com.recoder.capstone.dto.reserve;

import com.recoder.capstone.domain.value.ReserveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveItemSimpleDto {

    private Long reserveItemId;

    private String collegeName;

    private String parkingName;

    private String reserveDate;

    private Integer reserveTime;

    private ReserveStatus reserveStatus;
}
