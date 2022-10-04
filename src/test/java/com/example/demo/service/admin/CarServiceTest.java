package com.example.demo.service.admin;

import com.example.demo.domain.entity.Car;
import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CarServiceTest {

    @Autowired
    CarService carService;

    @Autowired
    CarRepository carRepository;

    @Test
    @DisplayName("차량 등록 취소")
    void cancelCar() {

        // given
        Long car_id = Long.valueOf(3);

        // when
        Car car = carRepository.getById(car_id);
        car.cancel();

    }

    @Test
    @DisplayName("차량 등록")
    void registerCar() {

        // given
        Long car_id = Long.valueOf(3);

        // when
        Car car = carRepository.getById(car_id);
        car.register();

    }

}