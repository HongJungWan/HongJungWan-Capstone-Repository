/*
 * 0509매핑 완료
 * */
package com.example.demo.domain.entity;

import com.example.demo.domain.value.ReserveStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase")
@Getter
public class Purchase {

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ReserveStatus status = ReserveStatus.COMP;

    @Column(name = "purchase_id")
    @Id
    @GeneratedValue
    private Long purchase_id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "car_num", nullable = false)
    private String car_num;

    @Column(name = "entry", nullable = false)
    private String entry;

    @Column(name = "departure", nullable = false)
    private String departure;


    @Builder(builderMethodName = "createPurchase")
    public Purchase(ReserveStatus status, Integer amount, String car_num, String entry, String departure) {
        this.status = status;
        this.amount = amount;
        this.car_num = car_num;
        this.entry = entry;
        this.departure = departure;
    }

}
