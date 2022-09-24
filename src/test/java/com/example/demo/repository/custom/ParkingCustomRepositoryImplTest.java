package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Parking;
import com.example.demo.repository.ParkingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ParkingCustomRepositoryImplTest {

    @Autowired
    private ParkingRepository parkingRepository;

    @Test
    @DisplayName("주차 가능, 주차구역 조회 쿼리 테스트")
    void findParking() {
        // given
        Long collegeId = Long.valueOf(3);
        String parkingName = "전기차 전용 구역";

        // when
        Parking parking = parkingRepository.findParking(collegeId, parkingName);

        // then
        assertThat(parking.getQuantity()).isEqualTo(30);
        assertThat(parking.isEnabled()).isTrue();
        assertThat(parking.getParkingName()).isEqualTo("전기차 전용 구역");
        assertThat(parking.getCollege().getId()).isEqualTo(3);
    }

    @Test
    @DisplayName("주차 불가능, 주차구역 조회 쿼리 테스트")
    void findParkingDisabled() {
        // given
        Long collegeId = Long.valueOf(12);
        String parkingName = "전기차 전용 구역";

        // when
        Parking parking = parkingRepository.findParking(collegeId, parkingName);

        // then
        assertThat(parking.getQuantity()).isEqualTo(5);
        assertThat(parking.getParkingName()).isEqualTo("전기차 전용 구역");
        assertThat(parking.getCollege().getId()).isEqualTo(12);
    }

}