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
    @GeneratedValue
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

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"college"})
    private final List<Parking> parkings = new ArrayList<>();

    @OneToMany(mappedBy = "college")
    private final List<Report> reports = new ArrayList<>();

    @Type(type = "yes_no")
    private Boolean enabled = true; // 예약 가능 여부

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

    public void setTotalParkingQuantity(Integer qty) {
        totalQuantity = qty;
    }

    public void setEnabled(boolean flag) {
        enabled = flag;
    }

    public void cancel() {
        totalQuantity++;
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

    public void hidden() {
        setEnabled(false);
    }

}
