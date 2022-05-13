package com.example.demo.dto.collage;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CollageListDto {

    private Long id;

    private String collageName;

    private String address;

    private Integer qty;

    private Boolean enabled;

}
