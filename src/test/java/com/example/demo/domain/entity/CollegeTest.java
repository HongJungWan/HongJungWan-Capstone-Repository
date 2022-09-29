package com.example.demo.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CollegeTest {

    @Test
    @DisplayName("대학 주차장 엔티티 테스트")
    void saveTest() {

        // given
        College college = College.createCollege()
                .address("")
                .collegeName("")
                .dateAccept(20)
                .detailAddress("")
                .build();

        // then
        assertThat(college.getCollegeName()).isEqualTo("");
        assertThat(college.getDateAccept()).isEqualTo(20);
        assertThat(college.getDetailAddress()).isEqualTo("");
        assertThat(college.getAddress()).isEqualTo("");

    }

    @Test
    @DisplayName("주차구역 예약 시, 주차장 자리 -1 증가")
    void removeStock() {

        // given
        College college = College.createCollege()
                .address("")
                .collegeName("")
                .dateAccept(20)
                .detailAddress("")
                .build();

        college.setTotalParkingQuantity(20);

        // when
        college.removeStock();

        // then
        assertThat(college.getTotalQuantity()).isEqualTo(19);

    }

    @Test
    @DisplayName("주차구역 예약 취소 시, 주차장 자리 +1 증가")
    void cancel() {

        // given
        College college = College.createCollege()
                .address("")
                .collegeName("")
                .dateAccept(20)
                .detailAddress("")
                .build();

        college.setTotalParkingQuantity(20);

        // when
        college.cancel();

        // then
        assertThat(college.getTotalQuantity()).isEqualTo(21);

    }

    @Test
    @DisplayName("주차장 삭제 (히든)")
    void hidden() {

        // given
        College college = College.createCollege()
                .address("")
                .collegeName("")
                .dateAccept(20)
                .detailAddress("")
                .build();

        college.setEnabled(true);

        // when
        college.hidden();

        // then
        assertThat(college.getEnabled()).isFalse();

    }

}