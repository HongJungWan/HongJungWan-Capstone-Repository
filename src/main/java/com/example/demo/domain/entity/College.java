/*
 * 0509 매핑 완료
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
@Table(name = "college")
@Getter
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "college_id")
    private Long id;

    @Column(name = "college_name", nullable = false)
    private String collegeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "date_accept")
    private Integer dateAccept;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    // true: y, false: n
    @Type(type = "yes_no")
    private Boolean enabled = true; // 예약 가능 여부

    // 0509 수정, JPA 영속성 전이, 삭제 안먹어서 PERSIST 에서 ALL로 수정
    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"college"})
    private final List<Parking> parkings = new ArrayList<>();

    @Builder(builderMethodName = "createCollege")
    public College(String collegeName, String address, String detailAddress, Integer dateAccept) {
        this.collegeName = collegeName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.dateAccept = dateAccept;

    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
        admin.getColleges().add(this);
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void cancel() {
        totalQuantity++;
    }

    public void updateDateAccept(Integer dateAccept) {
        this.dateAccept = dateAccept;
    }

    public void setTotalParkingQuantity(Integer qty) {
        totalQuantity = qty;
    }

    public void removeStock() {
        int restStock = totalQuantity - 1;
        if (restStock == 0) {
            setEnabled(false);
        }
        if (restStock < 0) {
            throw new NotEnoughStockException("예약 가능한 자리가 부족합니다.");
        }

        totalQuantity = restStock;
    }

    public void setEnabled(boolean flag) {
        enabled = flag;
    }

    // 연관관계 편의 메서드
    private void addAdmin(Admin admin) {
        this.admin = admin;
        admin.getColleges().add(this);
    }

    // 비즈니스 로직
    /**
     * 등록 취소
     */
    public void hidden() {

        this.setEnabled(false);
    }

}
