/*
 * 0509 매핑 완료
 * */
package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    // 양방향 0509 수정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Charge> charges = new ArrayList<>();

    @Column(name = "user_id")
    @Id
    @GeneratedValue
    private Long user_id;

    @Column(nullable = false)
    private String id;

    @Column(name = "phon_number", nullable = false)
    private String phonNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 0512 수정
    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = LAZY)
    private Car car;

    //0509 수정
    @Column(nullable = false)
    private String car_number;

    @Builder(builderMethodName = "createUser")
    public User(String id, String phonNumber, String password, String name,
                Gender gender, Integer age, Role role, String car_number) {
        this.id = id;
        this.phonNumber = phonNumber;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.role = role;
        this.car_number = car_number;
    }

}
