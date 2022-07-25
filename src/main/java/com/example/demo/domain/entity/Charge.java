/*0509 통과*/
package com.example.demo.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "charge")
@Getter
public class Charge {

    @Column(name = "charge_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long charge_id;

    @Column(name = "charge_name", nullable = false)
    private String charge_name;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "deadline")
    private String deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder(builderMethodName = "createCharge")
    public Charge(String charge_name, Integer amount, String deadline) {
        this.charge_name = charge_name;
        this.amount = amount;
        this.deadline = deadline;
    }

}
