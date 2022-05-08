/*
 * 매핑 완료
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
@Table(name = "reserve")
@Getter
public class Reserve {

    @Column(name = "reserve_id")
    @Id
    @GeneratedValue
    private Long reserve_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collage_id")
    private Collage Collage;

    @Enumerated(value = EnumType.STRING)
    private ReserveStatus status = ReserveStatus.COMP;

    @Column(name = "parking_area_name", nullable = false)
    private String parkingName;

    @Builder(builderMethodName = "createReserve")
    public Reserve(
            User user, Collage Collage, ReserveStatus status, String parkingName) {
        this.user = user;
        this.Collage = Collage;
        this.status = status;
        this.parkingName = parkingName;

    }

}
