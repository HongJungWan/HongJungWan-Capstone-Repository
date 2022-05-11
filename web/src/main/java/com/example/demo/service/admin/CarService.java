package com.example.demo.service.admin;

import com.example.demo.domain.entity.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.CarSearch;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    /**
     * 등록 취소
     */
    @Transactional
    public void cancelCar(Long carId) {
        //등록 엔티티 조회
        Car car = carRepository.findOne(carId);

        //등록 취소
        car.cancel(carId);
    }

    /**
     * 등록
     */
    @Transactional
    public void enrollCar(Long carId) {
        //등록 엔티티 조회
        Car car = carRepository.findOne(carId);

        //등록
        car.enroll(carId);
    }

    //검색
    public List<Car> findAll(CarSearch carSearch) {
        return carRepository.findAll(carSearch);
    }

}
