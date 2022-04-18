/*
* 매핑 완료
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

    @Id @GeneratedValue
    @Column(name = "parking_area_id")
    private Long id;

    @Column(name = "parking_area_name", nullable = false)
    private String parkingName;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    public void cancel() {
        this.quantity++;
        this.enabled=true;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collage_id")
    private Collage collage;

    @Type(type="yes_no")
    private boolean enabled = true;

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    @Builder(builderMethodName = "createParking")
    public Parking(String parkingName, Integer quantity) {
        this.parkingName = parkingName;
        this.quantity = quantity;

    }
    // 연관관계 편의 메서드
    public void addCollage(Collage collage) {
        this.collage = collage;
        collage.getParkings().add(this);
    }

    //==비즈니스 로직==//

    //예약 취소 시, 사용
    public void addStock(){
        this.quantity+=1;
    }

    //예약 시, 사용
    public void removeStock(){
        int restStock=this.quantity-1;
        if(restStock==0){
            setEnabled(false);
        }
        if(restStock<0){
            throw new NotEnoughStockException("예약 가능한 자리가 부족합니다.");
        }

        this.quantity=restStock;
    }

    //주차장 수정 시, 사용
    public void updateParkingQty(Integer quantity){
        this.quantity=quantity;
    }
}
