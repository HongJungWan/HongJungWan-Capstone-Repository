package com.example.demo.dto.car;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.value.EnrollStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListDto {

    private Long id;

    private String carNumber;

    private String userName;

    private EnrollStatus enrollStatus;

    public CarListDto(Car car) {
        id = car.getId();
        carNumber = car.getCar_number();
        userName = car.getUser().getName();
        enrollStatus = car.getStatus();
    }

}
