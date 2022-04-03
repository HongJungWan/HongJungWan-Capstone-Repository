package com.recoder.capstone.dto.college;

import com.recoder.capstone.domain.entity.College;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CollegeUpdateDto {

    private Long id;

    private String collegeName;

    private String startDate;

    private String endDate;

    @NotNull(message = "일일 최대 예약가능 차량을 입력해주세요.")
    private Integer dateAccept;

    private String startTime;

    private String endTime;
    @NotNull(message = "시간당 최대 예약가능 차량을 입력해주세요.")
    private Integer timeAccept;

    private String address;
    private String detailAddress;


    @NotNull(message = "남은 좌석 수를 입력해주세요.")
    private Integer a;
    @NotNull(message = "남은 좌석 수를 입력해주세요.")
    private Integer b;
    @NotNull(message = "남은 좌석 수를 입력해주세요.")
    private Integer c;
    @NotNull(message = "남은 좌석 수를 입력해주세요.")
    private Integer d;

    // 주차장 구역마다 남은 좌석 수를 달리하기 위해 Map 사용 (key:주차장 구역이름, value:잔여수령)
    private Map<String, Integer> parkingInfoMap = new HashMap<>();

    public College toCollegeEntity() {
        return College.createCollege()
                .collegeName(this.collegeName)
                .address(this.address)
                .detailAddress(this.detailAddress)
                .build();
    }

    @Builder(builderMethodName = "createCollegeUpdateDto")
    public CollegeUpdateDto(Long id, String collegeName, String startDate, String endDate, Integer dateAccept,
                            String startTime, String endTime, Integer timeAccept, String address,
                            String detailAddress, Integer a,
                            Integer b, Integer c, Integer d) {
        this.id=id;
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
