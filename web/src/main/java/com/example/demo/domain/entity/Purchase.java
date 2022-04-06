package com.example.demo.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase")
@Getter
public class Purchase {

    @Column(name = "purchase_id")
    @Id
    @GeneratedValue
    private Long purchase_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_id", nullable = false)
    private Charge charge;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Builder(builderMethodName = "createPurchase")
    public Purchase(Charge charge, User user) {

        this.charge = charge;
        this.user = user;
    }

}
