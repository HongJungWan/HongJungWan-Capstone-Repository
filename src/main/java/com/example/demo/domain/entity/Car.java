/*0509 통과*/
package com.example.demo.domain.entity;

import com.example.demo.domain.value.EnrollStatus;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "car")
@Getter
@Setter
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

    @Type(type = "yes_no")
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollStatus status; // 등록상태 [register, cancel]

    @Builder(builderMethodName = "createCar")
    public Car(String car_number, User user, EnrollStatus status) {
        this.car_number = car_number;
        this.user = user;
        this.status = status;
    }

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.setCar(this);
    }

    //==비즈니스 로직==//

    /**
     * 등록 취소
     */
    public void cancel() {

        setStatus(EnrollStatus.미_등록);
    }

    /**
     * 등록
     */
    public void register() {

        setStatus(EnrollStatus.등록);
    }

}
