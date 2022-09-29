package com.example.demo.domain.entity;

import com.example.demo.domain.value.EnrollStatus;
import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.Role;
import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.domain.value.EnrollStatus.등록;
import static com.example.demo.domain.value.EnrollStatus.미_등록;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CarTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    @DisplayName("차량 등록 취소 테스트")
    void cancel() {

        // given
        Car car = Car.createCar()
                .car_number("6가 123")
                .status(EnrollStatus.valueOf("등록"))
                .build();

        // when
        car.cancel();

        // then
        assertThat(car.getStatus()).isEqualTo(미_등록);

    }

    @Test
    @DisplayName("차량 등록 테스트")
    void register() {

        // given
        Car car = Car.createCar()
                .car_number("6가 123")
                .status(EnrollStatus.valueOf("미_등록"))
                .build();

        // when
        car.register();

        // then
        assertThat(car.getStatus()).isEqualTo(등록);

    }

    @Test
    @DisplayName("차량 엔티티 테스트")
    void saveTest() {

        // given
        User user = User.createUser()
                .id("1")
                .car_number("12가 1212")
                .name("홍")
                .password("1234")
                .gender(Gender.MALE)
                .age(25)
                .phonNumber("010-0000-0000")
                .email("test@naver.com")
                .role(Role.ROLE_USER)
                .build();

        // when
        Car car = Car.createCar()
                .car_number("6가 123")
                .user(user)
                .status(EnrollStatus.valueOf("미_등록"))
                .build();

        Car savedCar = carRepository.save(car);

        // then
        assertThat(savedCar.getCar_number()).isEqualTo("6가 123");
        assertThat(savedCar.getUser().getId()).isEqualTo("1");
        assertThat(savedCar.getStatus()).isEqualTo(미_등록);
        assertThat(savedCar.getUser()).isEqualTo(user);
        assertThat(savedCar.getEnabled()).isTrue();

    }

}