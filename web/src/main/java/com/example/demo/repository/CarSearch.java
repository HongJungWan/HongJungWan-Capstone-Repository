package com.example.demo.repository;

import com.example.demo.domain.entity.EnrollStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarSearch {

    private String userName; // 예약자 이름
    private EnrollStatus enrollStatus; //등록 상태[ENROLL, CANCEL]
}
