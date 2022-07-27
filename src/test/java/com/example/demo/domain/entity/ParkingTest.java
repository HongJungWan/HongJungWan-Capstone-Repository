package com.example.demo.domain.entity;

import com.example.demo.repository.custom.ParkingCustomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@WebAppConfiguration
class ParkingTest {

    @Qualifier("parkingRepository")
    @Autowired
    private ParkingCustomRepository parkingCustomRepository;

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

        Assertions.assertThat(parking.getQuantity()).isEqualTo(10);
    }

}