package com.example.demo.dto.reserve;

import com.example.demo.domain.value.ReserveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveSimpleDto {

    private Long reserveId;

    private String collegeName;

    private String parkingName;

    private ReserveStatus reserveStatus;
}
