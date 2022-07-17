package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.ReserveStatus;
import com.example.demo.domain.value.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReserveTest {

    @Test
    @DisplayName("예약 엔티티 테스트")
    void saveTest() {

        College college = College.createCollege()
                .address("")
                .collegeName("")
                .dateAccept(20)
                .detailAddress("")
                .build();

        User user = User.createUser()
                .id("1")
                .car_number("12가 1212")
                .name("홍")
                .password("1234")
                .gender(Gender.MALE)
                .age(25)
                .phonNumber("010-0000-0000")
                .email("test@naver.com")
                .role(Role.ROLE_USER)
                .build();

        Reserve reserve = Reserve.createReserve()
                .parkingName("test parking")
                .College(college)
                .status(ReserveStatus.COMP)
                .user(user)
                .build();

        Assertions.assertThat(reserve.getParkingName()).isEqualTo("test parking");
        Assertions.assertThat(reserve.getCollege().getDateAccept()).isEqualTo(20);
        Assertions.assertThat(reserve.getUser().getName()).isEqualTo("홍");
    }
}