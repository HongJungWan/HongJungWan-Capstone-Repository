/*
 *  매핑 완료
 * */
package com.example.demo.domain.entity;

import com.example.demo.exception.parking.NotEnoughStockException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "available_date")
@Getter
public class AvailableDate {

    @Id
    @GeneratedValue
    @Column(name = "available_data_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collage_id")
    private Collage collage;

    @Column(nullable = false)
    private String date;

    // 일일 수용 가능 인원
    @Column(name = "accept_count")
    private Integer acceptCount;

    public void cancel() {
        this.acceptCount++;
        this.enabled=true;
    }

    public void decreaseCount() {
        int restStock=this.acceptCount-1;
        if(restStock==0){
            setEnabled(false);
        }
        if(restStock<0){
            throw new NotEnoughStockException("예약 가능한 수량이 부족합니다.");
        }

        this.acceptCount=restStock;
    }

    @Type(type = "yes_no")
    private Boolean enabled = true;

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    // 양방향 연관관계 편의 메서드
    public void addCollage(Collage collage) {
        this.collage = collage;
        collage.getAvailableDates().add(this);
    }

    @Builder(builderMethodName = "createAvailableDate")
    public AvailableDate(String date, Integer acceptCount){
        this.date=date;
        this.acceptCount=acceptCount;
    }

    //주차장 상세내용 수정 시, count update
    public void updateAcceptCount(Integer acceptCount){
        this.acceptCount=acceptCount;
    }
}
