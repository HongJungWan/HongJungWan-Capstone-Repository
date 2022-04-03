package com.recoder.capstone.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReserveItemRequestDto {
    private Long collegeId;
    private String parkingName;
    private Long reserveDateId;
    private Long reserveTimeId;
}
