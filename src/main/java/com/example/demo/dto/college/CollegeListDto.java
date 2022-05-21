package com.example.demo.dto.college;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CollegeListDto {

    private Long id;

    private String collegeName;

    private String address;

    private Integer qty;

    private Boolean enabled;

}
