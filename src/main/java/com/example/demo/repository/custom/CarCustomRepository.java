package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.value.CarSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarCustomRepository {

    Page<Car> findAll(CarSearch carSearch, Pageable pageable);

    Car findOne(Long id);
}
