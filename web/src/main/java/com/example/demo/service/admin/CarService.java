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

    /**
     * 차량 정보 조회 후 dto로 변환
     */
//    public CarUpdateDto getCar(Long id) {
//        Optional<Car> carDetail = carRepository.findCarDetail(id);
//        Car car = carDetail.stream().findFirst().orElse(null);
//
//        return CarUpdateDto.createCarUpdateDto()
//                .carNumber(car.getCar_number())
//                .status(car.getStatus())
//                .build();
//    }


    /**
     * 차량 update
     */
//    public Long carUpdate(CarUpdateDto dto) throws ParseException {
//
//        Optional<Car> carDetail = carRepository.findCarDetail(dto.getCar_id());
//        Car car = carDetail.stream().findFirst().orElse(null);
//
//        carRepository.save(car);
//
//        return car.getId();
//    }

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
