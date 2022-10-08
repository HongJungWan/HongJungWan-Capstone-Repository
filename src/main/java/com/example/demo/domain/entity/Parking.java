/*
 * 0509매핑 완료
 * */
package com.example.demo.domain.entity;

import com.example.demo.exception.parking.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "parking_area")
@Getter
public class Parking {

    @Id
    @GeneratedValue
    @Column(name = "parking_area_id")
    private Long id;

    @Column(name = "parking_area_name", nullable = false)
    private String parkingName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;

    @Type(type = "yes_no")
    @Column(nullable = false)
    private boolean enabled = true;

    @Builder(builderMethodName = "createParking")
    public Parking(String parkingName, Integer quantity) {
        this.parkingName = parkingName;
        this.quantity = quantity;

    }

    public void setEnabled(boolean flag) {
        enabled = flag;
    }

    // 연관관계 편의 메서드
    public void addCollege(College college) {
        this.college = college;
        college.getParkings().add(this);
    }

    //예약 시, 사용
    public void removeStock() {
        int restStock = quantity - 1;
        if (restStock == 0) {
            setEnabled(false);
        }
        if (restStock < 0) {
            throw new NotEnoughStockException("예약 가능한 자리가 부족합니다.");
        }

        quantity = restStock;
    }

    public void cancel() {
        quantity++;
        enabled = true;
    }

    //주차장 수정 시, 사용
    public void updateParkingQty(Integer quantity) {
        this.quantity = quantity;
    }

}
