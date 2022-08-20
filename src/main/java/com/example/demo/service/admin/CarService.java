package com.example.demo.service.admin;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.value.CarSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    /**
     * 동적 쿼리, 검색
     */
    Page<Car> findAll(CarSearch carSearch, Pageable pageable);

    /**
     * 등록 취소
     */
    void cancelCar(Long car_id);

    /**
     * 등록
     */
    void registerCar(Long car_id);
    
}
