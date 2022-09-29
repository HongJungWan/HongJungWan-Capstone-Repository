package com.example.demo.repository;

import com.example.demo.domain.entity.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    @DisplayName("Spirng Data JPA랑 순수 JPA 결과 비교")
    void findByAll() {
        List<Car> list = carRepository.findAll();

        for (Car car : list) {
            System.out.println("CarRepositoryTest.findByAll() = " + car.getCar_number());
        }

        assertThat(list.size()).isEqualTo(17);
    }

}