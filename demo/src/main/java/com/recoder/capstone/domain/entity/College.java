package com.recoder.capstone.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recoder.capstone.exception.parking.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "college")
@Getter
public class College extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "college_id")
    private Long id;

    @Column(name = "college_name")
    private String collegeName;

    // 양방향
    @OneToMany(mappedBy = "college",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"college"})
    private List<AvailableDate> availableDates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public void setAdmin(Admin admin) {
        this.admin = admin;
        admin.getColleges().add(this);
    }

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    public void cancel() {
        this.totalQuantity++;
    }

    @Column(name = "date_accept")
    private Integer dateAccept;
    @Column(name = "time_accept")
    private Integer timeAccept;

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

    public void updateDateAccept(Integer dateAccept){this.dateAccept=dateAccept;}

    public void updateTimeAccept(Integer timeAccept){this.timeAccept=timeAccept;}

    // true: y, false: n
    @Type(type = "yes_no")
    private Boolean enabled = true; // 예약 가능 여부

    @OneToMany(mappedBy = "college", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnoreProperties({"college"})
    private List<Parking> parkings = new ArrayList<>();

    public void setEnabled(boolean flag) {
        this.enabled = flag;
    }

    // 연관관계 편의 메서드
    private void addAdmin(Admin admin) {
        this.admin = admin;
        admin.getColleges().add(this);
    }


    @Builder(builderMethodName = "createCollege")
    public College(String collegeName, String address, String detailAddress, Integer dateAccept, Integer timeAccept) {
        this.collegeName = collegeName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.createAt = LocalDateTime.now();
        this.dateAccept=dateAccept;
        this.timeAccept=timeAccept;
    }
}
