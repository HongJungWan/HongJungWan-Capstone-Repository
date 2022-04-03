package com.recoder.hong.domain.entity;

import com.recoder.hong.exception.parkingarea.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "parkingarea")
@Getter
public class ParkingArea extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "parkingarea_id")
    private Long id;

    @Column(name = "parkingarea_name", nullable = false)
    private String parkingareaName;

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

    @Builder(builderMethodName = "createParkingArea")
    public ParkingArea(String parkingareaName, Integer quantity) {
        this.parkingareaName = parkingareaName;
        this.quantity = quantity;

        this.createAt = LocalDateTime.now();
    }
    // 연관관계 편의 메서드
    public void addCollege(College college) {
        this.college = college;
        college.getParkingareas().add(this);
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
            throw new NotEnoughStockException("예약 가능한 주차장 자리가 부족합니다.");
        }

        this.quantity=restStock;
    }

    //병원 수정 시, 사용
    public void updateParkingAreaQty(Integer quantity){
        this.quantity=quantity;
    }
}
