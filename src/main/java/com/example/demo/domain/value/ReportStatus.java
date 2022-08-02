package com.example.demo.domain.value;

import lombok.Getter;

@Getter
public enum ReportStatus {

    REPORT("처리 필요"), PROCESS("처리 진행"), COMPLETION("처리 완료");

    private final String description;

    ReportStatus(String description) {
        this.description = description;
    }
}
