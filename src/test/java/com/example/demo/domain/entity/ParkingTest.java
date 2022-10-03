package com.example.demo.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ParkingTest {

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

        assertThat(parking.getQuantity()).isEqualTo(10);

    }

    @Test
    @DisplayName("주차구역 예약 취소 시, 자리 +1 증가")
    void cancel() {

        // given
        Parking parking = Parking.createParking()
                .quantity(10)
                .build();

        // when
        parking.cancel();

        // then
        assertThat(parking.getQuantity()).isEqualTo(11);

    }

    @Test
    @DisplayName("주차구역 예약 시, 자리 -1 증가")
    void removeStock() {

        // given
        Parking parking = Parking.createParking()
                .quantity(10)
                .build();

        // when
        parking.removeStock();

        // then
        assertThat(parking.getQuantity()).isEqualTo(9);

    }

    @Test
    @DisplayName("주차장 수정 시, 값 제대로 수정되는지 테스트")
    void updateParkingQty() {

        // given
        Integer quantity = 20;

        Parking parking = Parking.createParking()
                .quantity(10)
                .build();

        // when
        parking.updateParkingQty(quantity);

        // then
        assertThat(parking.getQuantity()).isEqualTo(20);

    }

}