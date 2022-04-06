package com.example.demo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "car")
@Getter
public class Car {

    @Column(name = "car_id")
    @Id
    @GeneratedValue
    private Long car_id;

    @Column(name = "car_number", nullable =false)
    private String car_number;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder(builderMethodName = "createCar")
    public Car( String car_number, User user) {

        this.car_number=car_number;
        this.user = user;
    }

}
