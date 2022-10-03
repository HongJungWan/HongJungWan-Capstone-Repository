package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
                .charge_name("구매 테스트")
                .amount(Integer.valueOf(3000))
                .deadline("0929")
                .build();

        assertThat(charge.getAmount()).isEqualTo(3000);
        assertThat(charge.getCharge_id()).isNull();
        assertThat(charge.getCharge_name()).isEqualTo("구매 테스트");
        assertThat(charge.getDeadline()).isEqualTo("0929");

    }

}