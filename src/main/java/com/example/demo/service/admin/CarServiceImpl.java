package com.example.demo.service.admin;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.value.CarSearch;
import com.example.demo.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;

    /**
     * 동적 쿼리, 검색
     */
    public List<Car> findAll(CarSearch carSearch) {
        return carRepository.findAll(carSearch);
    }


    /**
     * 등록 취소
     */
    @Transactional
    public void cancelCar(Long car_id) {

        //등록 엔티티 조회
        Car car = carRepository.findOne(car_id);

        //등록 취소
        car.cancel();
    }

    /**
     * 등록
     */
    @Transactional
    public void registerCar(Long car_id) {

        //등록 엔티티 조회
        Car car = carRepository.findOne(car_id);

        //등록
        car.register();
    }

}
