package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Parking;
import com.example.demo.repository.ReserveRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReserveCustomRepositoryImplTest {

    @Autowired
    private ReserveRepository reserveRepository;

    @Test
    @DisplayName("이용 가능한 주차 구역 조회")
    void findAvailableParkings() {
        // given
        Long collegeId = Long.valueOf(3);

        // when
        List<Parking> parking = reserveRepository.findAvailableParkings(collegeId);

        // then
        assertThat(parking.size()).isEqualTo(4);
        assertThat(parking.get(3).isEnabled()).isTrue();

    }

}