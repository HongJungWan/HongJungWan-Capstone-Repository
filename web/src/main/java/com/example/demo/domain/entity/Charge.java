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
    @GeneratedValue
    private Long charge_id;

    @Column(name = "charge_name", nullable = false)
    private String charge_name;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collage_id", nullable = false)
    private Collage collage;

    // 양방향 연관관계 편의 메서드 0408
    public void addCollage(Collage collage) {
        this.collage = collage;
        collage.getCharges().add(this);
    }

    @Builder(builderMethodName = "createCharge")
    public Charge(String charge_name, Integer amount, Collage collage) {
        this.charge_name = charge_name;
        this.amount = amount;
        this.collage = collage;
    }

}
