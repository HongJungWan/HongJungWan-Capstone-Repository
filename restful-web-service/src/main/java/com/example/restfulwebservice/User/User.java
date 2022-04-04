package com.example.restfulwebservice.User;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"password"})
//@JsonFilter("UserInfo") // Controller, Service 클래스에서 사용,
// Filter ID를 문자열로 지정, 이 어노테이션 사용 시 무조건
// FilterProvider와 해당 ID를 처리하는 필터를 제공해야함.
public class User {
    private Integer id;

    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.") // 최소 길이 2개 이상
    private String name;

    @Past // 미래 데이터를 사용할 수 없다는 제약 조건
    private Date joinDate;

    private String password;
    private String ssn;
}
