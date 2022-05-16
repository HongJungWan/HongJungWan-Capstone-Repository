package com.example.demo.service.admin;

import com.example.demo.domain.entity.Car;
import com.example.demo.repository.custom.CarCustomRepositoryImpl;
import com.example.demo.repository.CarSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarService {

    private final CarCustomRepositoryImpl carCustomRepositoryImpl;

    //검색
    public List<Car> findAll(CarSearch carSearch) {
        return carCustomRepositoryImpl.findAll(carSearch);
    }


    /**
     * 등록 취소
     */
    @Transactional
    public void cancelCar(Long car_id) {

        //등록 엔티티 조회
        Car car = carCustomRepositoryImpl.findOne(car_id);

        //등록 취소
        car.cancel();
    }

    /**
     * 등록
     */
    @Transactional
    public void registerCar(Long car_id) {

        //등록 엔티티 조회
        Car car = carCustomRepositoryImpl.findOne(car_id);

        //등록
        car.register();
    }

}
