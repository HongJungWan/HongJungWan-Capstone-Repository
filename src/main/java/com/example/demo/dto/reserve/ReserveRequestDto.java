package com.example.demo.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReserveRequestDto {

    private Long collegeId;

    private String parkingName;

}
