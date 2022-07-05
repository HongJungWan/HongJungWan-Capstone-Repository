package com.example.demo.service.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestDTO {
    private String title;
    private String body;
    private String targetToken;
}
