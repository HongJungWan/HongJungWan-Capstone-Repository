package com.example.demo.dto.college;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 주차장 조회 결과를 위한 dto
 * json 포맷을 빈 값 없이 맞추기 위해 CollegeRequestDto와 따로 사용
 */

@Data
@NoArgsConstructor
public class CollegeResponseDto {
    private String collegeName;
    private String address;
    private String detailAddress;

    private Map<String, Integer> parkingInfoMap = new HashMap<>();

    public CollegeResponseDto createDto(String collegeName, String address, String detailAddress, Map<String, Integer> parkingInfoMap) {
        this.collegeName = collegeName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.parkingInfoMap = parkingInfoMap;

        return this;
    }
}
