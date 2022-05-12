package com.example.demo.dto.car;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.entity.EnrollStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CarUpdateDto {

    private Long car_id;

    private String carNumber;

    @NotNull(message = "등록 상태를 입력해주세요. (등록 / 미_등록)")
    private EnrollStatus status;


    @Builder(builderMethodName = "createCarUpdateDto")
    public CarUpdateDto(Long car_id, String carNumber, EnrollStatus status) {
        this.car_id = car_id;
        this.carNumber = carNumber;
        this.status = status;
    }

    public Car toCarEntity() {
        return Car.createCar()
                .car_number(carNumber)
                .status(status)
                .build();
    }

}
