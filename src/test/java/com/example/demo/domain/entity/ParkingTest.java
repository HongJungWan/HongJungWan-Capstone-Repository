package com.example.demo.domain.entity;

import com.example.demo.repository.ParkingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ParkingTest {

    @Autowired
    private ParkingRepository parkingRepository;

    @Test
    @DisplayName("주차 구역 엔티티 테스트")
    void saveTest() {

        Admin admin = Admin.createAdmin()
                .id(Long.valueOf(1))
                .name("admin")
                .password("admin")
                .build();

        College college = College.createCollege()
                .address("")
                .collegeName("test")
                .dateAccept(30)
                .detailAddress("")
                .build();

        Parking parking = Parking.createParking()
                .parkingName("")
                .quantity(10)
                .build();

        Parking savedParking = parkingRepository.save(parking);

        Assertions.assertThat(parking.getQuantity()).isEqualTo(10);

//        Assertions.assertThat(parking.getCollege()).isEqualTo("test");
    }
}