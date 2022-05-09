package com.example.demo.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "departure")
@Getter
public class Departure {

    @Column(name = "timeDeparture")
    @Id
    @GeneratedValue
    private Long timeDeparture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder(builderMethodName = "createCharge")
    public Departure(Long timeDeparture, User user) {
        this.timeDeparture = timeDeparture;
        this.user = user;
    }

}
