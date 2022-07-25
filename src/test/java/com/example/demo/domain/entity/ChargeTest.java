package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ChargeTest {

    @Test
    @DisplayName("비용 엔티티 테스트")
    void saveTest() {

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

        Charge charge = Charge.createCharge()
                .charge_name("")
                .amount(Integer.valueOf(3000))
                .deadline("")
                .build();

        Assertions.assertThat(charge.getAmount()).isEqualTo(3000);
    }

}