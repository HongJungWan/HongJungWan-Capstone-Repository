package com.example.demo.service.admin;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.entity.EnrollStatus;
import com.example.demo.dto.car.CarUpdateDto;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.CarSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    //검색
    public List<Car> findAll(CarSearch carSearch) {
        return carRepository.findAll(carSearch);
    }

    /**
     * 차량 정보 조회 후 dto로 변환
     */
    public CarUpdateDto getCar(Long id) {
        Optional<Car> carDetail = carRepository.findCarDetail(id);
        Car car = carDetail.stream().findFirst().orElse(null);


        return CarUpdateDto.createCarUpdateDto()
                .carNumber(car.getCar_number())
                .status(car.getStatus())
                .build();
    }


    /**
     * 차량 update
     */
    public Long carUpdate(CarUpdateDto dto) throws ParseException {

        Optional<Car> carDetail = carRepository.findCarDetail(dto.getCar_id());
        Car car = carDetail.stream().findFirst().orElse(null);

        if (dto.getStatus().equals("등록")) {
            car.setStatus(EnrollStatus.valueOf("등록"));
            carRepository.save(car);
        } else if (dto.getStatus().equals("미_등록")) {
            car.setStatus(EnrollStatus.valueOf("미_등록"));
            carRepository.save(car);
        }

        return car.getId();
    }

}
