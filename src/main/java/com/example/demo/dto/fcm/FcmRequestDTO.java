package com.example.demo.dto.fcm;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FcmRequestDTO {
    private String title;
    private String body;
    private String targetToken;

}

