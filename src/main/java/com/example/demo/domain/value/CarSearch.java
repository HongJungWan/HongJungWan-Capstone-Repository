package com.example.demo.domain.value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarSearch {

    private String userName; // 예약자 이름
    private EnrollStatus enrollStatus; // [등록, 미_등록]
}
