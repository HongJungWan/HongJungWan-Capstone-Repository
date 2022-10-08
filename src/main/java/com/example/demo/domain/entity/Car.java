/*0509 통과*/
package com.example.demo.domain.entity;

import com.example.demo.domain.value.EnrollStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "car")
@Getter
public class Car {

    @Column(name = "car_id")
    @Id
    @GeneratedValue()
    private Long id;

    @Column(name = "car_number", nullable = false)
    private String car_number;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollStatus status; // 등록상태 [register, cancel]

    @Type(type = "yes_no")
    private final Boolean enabled = true;

    @Builder(builderMethodName = "createCar")
    public Car(String car_number, User user, EnrollStatus status) {
        this.car_number = car_number;
        this.user = user;
        this.status = status;
    }

    public void setStatus(EnrollStatus enrollStatus) {
        status = enrollStatus;
    }

    public void cancel() {
        setStatus(EnrollStatus.미_등록);
    }

    public void register() {
        setStatus(EnrollStatus.등록);
    }

}
