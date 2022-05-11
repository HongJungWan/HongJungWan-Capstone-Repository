/*0509 통과*/
package com.example.demo.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "car")
@Getter
@Setter
public class Car {

    @Column(name = "car_id")
    @Id
    @GeneratedValue
    private Long car_id;

    @Column(name = "car_number", nullable = false)
    private String car_number;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private EnrollStatus status; // 등록상태 [ENROLL, CANCEL]

    @Builder(builderMethodName = "createCar")
    public Car(String car_number, User user, EnrollStatus status) {

        this.car_number = car_number;
        this.user = user;
        this.status = status;
    }

    //==비즈니스 로직==//

    /**
     * 등록 취소
     */
    public void cancel(Long carId) {

        setStatus(EnrollStatus.CANCEL);
    }

    /**
     * 등록
     */
    public void enroll(Long carId) {

        setStatus(EnrollStatus.ENROLL);
    }

}
