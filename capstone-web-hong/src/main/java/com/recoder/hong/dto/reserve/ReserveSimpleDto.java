package com.recoder.hong.dto.reserve;

import com.recoder.hong.domain.value.ReserveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveSimpleDto {

    private Long reserveId;

    private String collegeName;

    private String parkingareaName;

    private String reserveDate;

    private Integer reserveTime;

    private ReserveStatus reserveStatus;
}
