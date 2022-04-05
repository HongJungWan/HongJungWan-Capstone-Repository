/*
* 매핑 완료
* */

package com.example.demo.domain.entity;

import com.example.demo.domain.entity.AvailableDate;
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
@Table(name = "collage")
@Getter
public class Collage {

    @Id @GeneratedValue
    @Column(name = "collage_id")
    private Long id;

    @Column(name = "collage_name",nullable = false)
    private String collageName;

    // 양방향
    @OneToMany(mappedBy = "collage",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"collage"})
    private List<AvailableDate> availableDates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
        admin.getCollages().add(this);
    }

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "detail_address",nullable = false)
    private String detailAddress;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    public void cancel() {
        this.totalQuantity++;
    }


    public void setTotalParkingQuantity(Integer qty) {
        this.totalQuantity = qty;
    }

    public void removeStock() {
        int restStock=this.totalQuantity-1;
        if(restStock==0){
            setEnabled(false);
        }
        if(restStock<0){
            throw new NotEnoughStockException("예약 가능한 수량이 부족합니다.");
        }

        this.totalQuantity=restStock;
    }

    // true: y, false: n
    @Type(type = "yes_no")
    private Boolean enabled = true; // 예약 가능 여부

    @OneToMany(mappedBy = "collage", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnoreProperties({"collage"})
    private List<Parking> parkings = new ArrayList<>();

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    // 연관관계 편의 메서드
    private void addAdmin(Admin admin) {
        this.admin = admin;
        admin.getCollages().add(this);
    }


    @Builder(builderMethodName = "createCollage")
    public Collage(String collageName, String address, String detailAddress ) {
        this.collageName = collageName;
        this.address = address;
        this.detailAddress = detailAddress;

    }
}
