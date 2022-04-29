package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
@Getter
public class User {

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

    @Builder(builderMethodName = "createUser")
    public User(String id, String phonNumber, String password, String name,
                Gender gender, Integer age, Role role) {
        this.id = id;
        this.phonNumber = phonNumber;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.role = role;
    }
}
