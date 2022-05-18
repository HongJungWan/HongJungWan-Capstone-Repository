package com.example.demo.dto.college;


import com.example.demo.domain.entity.College;
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

    @NotNull(message = "일일 최대 예약가능 차량을 입력해주세요.")
    private Integer dateAccept;

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

    @Builder(builderMethodName = "createCollegeUpdateDto")
    public CollegeUpdateDto(Long id, String collegeName, Integer dateAccept,
                            String address, String detailAddress, Integer a, Integer b, Integer c, Integer d) {

        this.id = id;
        this.collegeName = collegeName;
        this.dateAccept = dateAccept;
        this.address = address;
        this.detailAddress = detailAddress;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

    }

    public College toCollegeEntity() {
        return College.createCollege()
                .collegeName(collegeName)
                .address(address)
                .detailAddress(detailAddress)
                .build();
    }
}
