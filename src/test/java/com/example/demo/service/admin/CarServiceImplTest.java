package com.example.demo.service.admin;

import com.example.demo.domain.entity.Car;
import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarServiceImplTest {

    @Autowired
    CarRepository carRepository;

    @Test
    void cancelCar(Long car_id) {
        Car car = carRepository.findOne(car_id);
        //주문 취소
        car.cancel();
    }
}