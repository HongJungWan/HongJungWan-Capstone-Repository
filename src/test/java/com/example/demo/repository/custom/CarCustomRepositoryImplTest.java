package com.example.demo.repository.custom;

import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarCustomRepositoryImplTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    @DisplayName("차량 목록 동적 쿼리 조회 테스트")
    void findAll() {
        // given

        // when

        // then

    }

}