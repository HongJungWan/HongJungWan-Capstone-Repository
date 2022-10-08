/*
 * 0509 매핑 완료
 * */
package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
@Getter
public class User {

    @Column(name = "user_id")
    @Id
    @GeneratedValue()
    private Long user_id;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String email;

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

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = LAZY)
    private Car car;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Purchase> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Charge> charges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<Report> reports = new ArrayList<>();

    @Column
    private String car_number;

    @Builder(builderMethodName = "createUser")
    public User(String id, String phonNumber, String password, String name,
                Gender gender, Integer age, Role role, String car_number, String email) {
        this.id = id;
        this.phonNumber = phonNumber;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.role = role;
        this.car_number = car_number;
        this.email = email;
    }

}
