package com.example.demo.service.admin;

import com.example.demo.domain.entity.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.CarSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    //검색
    public List<Car> findAll(CarSearch carSearch) {
        return carRepository.findAll(carSearch);
    }

}
