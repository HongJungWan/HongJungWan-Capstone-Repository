package com.example.demo.domain.value;

import lombok.Getter;

@Getter
public enum ReportStatus {

    PROCESS("처리 중"), COMPLETION("처리 완료");

    private final String description;

    ReportStatus(String description) {
        this.description = description;
    }
}
