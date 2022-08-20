package com.example.demo.domain.value;

import lombok.Getter;

@Getter
public enum EnrollStatus {
    등록("등록"), 미_등록("미_등록");

    private final String description;

    EnrollStatus(String description) { this.description = description; }
}
