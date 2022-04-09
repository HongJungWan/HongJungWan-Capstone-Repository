package com.example.demo.dto.collage;


import com.example.demo.domain.entity.Collage;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 어드민으로부터 주차장등록 요청을 처리하기 위한 DTO
 */

@Data
@NoArgsConstructor
public class CollageRequestDto {

    @NotEmpty(message = "주차장이름을 입력해주세요.")
    private String collageName;

    @NotEmpty(message = "예약가능 시작날짜를 선택해주세요.")
    private String startDate;
    @NotEmpty(message = "예약가능 종료날짜를 선택해주세요.")
    private String endDate;

    @NotEmpty(message = "주차장 주소를 입력해주세요.")
    private String address;
    @NotEmpty(message = "주차장 상세주소를 입력해주세요.")
    private String detailAddress;



    private List<String> parkingNames = new ArrayList<>();

    private List<Integer> parkingQuantities = new ArrayList<>();

    @NotNull(message = "운영 가능한 수를 입력해주세요.")
    private Integer a;
    @NotNull(message = "운영 가능한 수를 입력해주세요.")
    private Integer b;
    @NotNull(message = "운영 가능한 수를 입력해주세요.")
    private Integer c;
    @NotNull(message = "운영 가능한 수를 입력해주세요.")
    private Integer d;

    // 주차장 구역마다 운영 가능한 수를 달리하기 위해 Map 사용 (key:주차장 구역이름, value:잔여 좌석?)
    private Map<String, Integer> parkingInfoMap = new HashMap<>();

    public Collage toCollageEntity() {
        return Collage.createCollage()
                .collageName(this.collageName)
                .address(this.address)
                .detailAddress(this.detailAddress)
                .build();
    }

    @Builder(builderMethodName = "createCollageRequestDto")
    public CollageRequestDto(String collageName, String startDate, String endDate, String address,
                             String detailAddress, Integer a,
                             Integer b, Integer c, Integer d) {
        this.collageName = collageName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.detailAddress = detailAddress;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

    }

}


