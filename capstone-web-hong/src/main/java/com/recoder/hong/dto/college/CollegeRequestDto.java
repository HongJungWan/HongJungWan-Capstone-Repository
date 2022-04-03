package com.recoder.hong.dto.college;

import com.recoder.hong.domain.entity.College;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class CollegeRequestDto {
    
    @NotEmpty(message = "주차장 이름을 입력해주세요.")
    private String collegeName;

    @NotEmpty(message = "예약가능 시작날짜를 선택해주세요.")
    private String startDate;
    @NotEmpty(message = "예약가능 종료날짜를 선택해주세요.")
    private String endDate;
    @NotNull(message = "일일 최대 운영가능 인원을 입력해주세요.")
    private Integer dateAccept;

    @NotNull(message = "운영가능 시작시간을 선택해주세요.")
    private String startTime;
    @NotNull(message = "운영가능 종료시간을 선택해주세요.")
    private String endTime;
    @NotNull(message = "시간당 최대 운영가능 인원을 입력해주세요.")
    private Integer timeAccept;

    @NotEmpty(message = "주차장 주소를 입력해주세요.")
    private String address;
    @NotEmpty(message = "주차장 상세주소를 입력해주세요.")
    private String detailAddress;



    private List<String> parkingareaNames = new ArrayList<>();

    private List<Integer> parkingareaQuantities = new ArrayList<>();

    @NotNull(message = "주차장 운영 좌석 수를 입력해주세요.")
    private Integer a;
    @NotNull(message = "주차장 운영 좌석 수를 입력해주세요.")
    private Integer b;
    @NotNull(message = "주차장 운영 좌석 수를 입력해주세요.")
    private Integer c;
    @NotNull(message = "주차장 운영 좌석 수를 입력해주세요.")
    private Integer d;

    // 주차 구역 종류마다 남은 자리 수를 달리하기 위해 Map 사용 (key:주차 구역명, value:남은 자리)
    private Map<String, Integer> parkingareaInfoMap = new HashMap<>();

    public College toCollegeEntity() {
        return College.createCollege()
                .collegeName(this.collegeName)
                .address(this.address)
                .detailAddress(this.detailAddress)
                .dateAccept(dateAccept)
                .timeAccept(timeAccept)
                .build();
    }

    @Builder(builderMethodName = "createCollegeRequestDto")
    public CollegeRequestDto(String CollegeName, String startDate, String endDate, Integer dateAccept,
                              String startTime, String endTime, Integer timeAccept, String address,
                              String detailAddress, Integer a,
                              Integer b, Integer c, Integer d) {
        this.collegeName = collegeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateAccept = dateAccept;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeAccept = timeAccept;
        this.address = address;
        this.detailAddress = detailAddress;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

    }

}
