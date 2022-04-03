package com.recoder.capstone.domain.entity;

import com.recoder.capstone.exception.parking.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "parking")
@Getter
public class Parking extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "parking_id")
    private Long id;

    @Column(name = "parking_name", nullable = false)
    private String parkingName;

    @Column(nullable = false)
    private Integer quantity;

    public void cancel() {
        this.quantity++;
        this.enabled=true;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;

    @Type(type="yes_no")
    private boolean enabled = true;

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    @Builder(builderMethodName = "createParking")
    public Parking(String parkingName, Integer quantity) {
        this.parkingName = parkingName;
        this.quantity = quantity;

        this.createAt = LocalDateTime.now();
    }
    // 연관관계 편의 메서드
    public void addCollege(College college) {
        this.college = college;
        college.getParkings().add(this);
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
            throw new NotEnoughStockException("예약 가능한 수량이 부족합니다.");
        }

        this.quantity=restStock;
    }

    //주차장 수정 시, 사용
    public void updateParkingQty(Integer quantity){
        this.quantity=quantity;
    }
}
