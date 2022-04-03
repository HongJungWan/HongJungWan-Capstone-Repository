package com.recoder.hong.domain.entity;


import com.recoder.hong.domain.value.Gender;
import com.recoder.hong.domain.value.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
@Getter
public class User extends BaseEntity{

    @Column(name = "user_id")
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder(builderMethodName = "createUser")
    public User(String email, String password, String name,
                Gender gender, Integer age, String address, String detailAddress, Role role) {
        this.email = email;
        this.password = password;
        this.name=name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.detailAddress = detailAddress;
        this.role = role;
        this.createAt = LocalDateTime.now();
    }
}
