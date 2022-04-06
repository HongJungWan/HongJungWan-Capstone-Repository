package com.example.demo.domain.value;

import lombok.Getter;

@Getter
public enum ReserveStatus {

    COMP("예약완료"), CANCEL("예약취소");

    private String description;

    ReserveStatus(String description) {
        this.description = description;
    }
}
