package com.example.demo.service.admin;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.value.CarSearch;

import java.util.List;

public interface CarService {

    /**
     * 동적 쿼리, 검색
     */
    List<Car> findAll(CarSearch carSearch);

    /**
     * 등록 취소
     */
    void cancelCar(Long car_id);

    /**
     * 등록
     */
    void registerCar(Long car_id);
    
}
