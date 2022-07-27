package com.example.demo.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.domain.value.ReserveStatus.COMP;

@SpringBootTest
@Transactional
@WebAppConfiguration
class PurchaseTest {

    @Test
    @DisplayName("구매 엔티티 테스트")
    void saveTest() {

        Purchase purchase = Purchase.createPurchase()
                .amount(3000)
                .car_num("12가 test")
                .entry("22-07-16")
                .departure("22-07-16")
                .status(COMP)
                .build();

        Assertions.assertThat(purchase.getAmount()).isEqualTo(3000);
        Assertions.assertThat(purchase.getStatus()).isEqualTo(COMP);
        Assertions.assertThat(purchase.getEntry()).isEqualTo("22-07-16");
    }

}