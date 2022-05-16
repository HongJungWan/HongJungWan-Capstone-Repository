package com.example.demo.repository;

import com.example.demo.domain.entity.Car;
import com.example.demo.repository.custom.CarCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long>, CarCustomRepository {

}
