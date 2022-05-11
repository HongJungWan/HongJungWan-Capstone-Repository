package com.example.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnrollStatus {
    등록, 미_등록
}
