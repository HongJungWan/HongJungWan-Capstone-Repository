package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.value.CarSearch;

import java.util.List;

public interface CarCustomRepository {

    List<Car> findAll(CarSearch carSearch);

    Car findOne(Long id);
}
