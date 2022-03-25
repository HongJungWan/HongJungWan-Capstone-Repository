package com.example.restfulwebservice.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    private Integer id;

    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.") // 최소 길이 2개 이상
    private String name;

    @Past // 미래 데이터를 사용할 수 없다는 제약 조건
    private Date joinDate;
}
