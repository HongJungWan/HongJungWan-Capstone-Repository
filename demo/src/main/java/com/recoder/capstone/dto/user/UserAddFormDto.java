package com.recoder.capstone.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 회원 가입에 사용할 폼
 */
@Data
public class UserAddFormDto {

    @NotNull(message = "이메일을 입력 해 주세요")
    private String email;

    private String password;

    @NotEmpty(message = "이름을 입력 해 주세요")
    private String name;

    @NotEmpty(message = "성별을 체크 해 주세요")
    private String gender;

    @NotNull(message = "나이를 입력 해 주세요")
    private Integer age;

    @NotEmpty(message = "주소를 입력 해 주세요")
    private String address;

    @NotEmpty(message = "상세 주소를 입력 해 주세요")
    private String detailAddress;

}
