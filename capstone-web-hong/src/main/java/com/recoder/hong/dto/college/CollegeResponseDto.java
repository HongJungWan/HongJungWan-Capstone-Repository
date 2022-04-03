package com.recoder.hong.dto.college;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * College 조회 결과를 위한 dto
 * json 포맷을 빈 값 없이 맞추기 위해 CollegeRequestDto와 따로 사용
 */

public class CollegeResponseDto {

    private String collegeName;
    private List<String> availableDates;
    private String address;
    private String detailAddress;

    private Map<String, Integer> parkingareaInfoMap = new HashMap<>();

    public CollegeResponseDto createDto(String collegeName, List<String> availableDates,
                                         String address, String detailAddress, Map<String, Integer> parkingareaInfoMap) {
        this.collegeName = collegeName;
        this.availableDates = availableDates;
        this.address = address;
        this.detailAddress = detailAddress;
        this.parkingareaInfoMap = parkingareaInfoMap;

        return this;
    }

}
