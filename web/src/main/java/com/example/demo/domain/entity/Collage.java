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
@Table(name = "collage")
@Getter
public class Collage {

    // 0509 수정, JPA 영속성 전이, 삭제 안먹어서 PERSIST 에서 ALL로 수정
    @OneToMany(mappedBy = "collage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"collage"})
    private final List<Parking> parkings = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "collage_id")
    private Long id;

    @Column(name = "collage_name", nullable = false)
    private String collageName;

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

    @Builder(builderMethodName = "createCollage")
    public Collage(String collageName, String address, String detailAddress, Integer dateAccept) {
        this.collageName = collageName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.dateAccept = dateAccept;

    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
        admin.getCollages().add(this);
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
        admin.getCollages().add(this);
    }

    // 비즈니스 로직
    /**
     * 등록 취소
     */
    public void hidden() {

        this.setEnabled(false);
    }

}
