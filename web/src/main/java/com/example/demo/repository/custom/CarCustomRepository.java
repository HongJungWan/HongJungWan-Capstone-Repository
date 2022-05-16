package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Car;
import com.example.demo.repository.CarSearch;

import java.util.List;

public interface CarCustomRepository {

    List<Car> findAll(CarSearch carSearch);
}
