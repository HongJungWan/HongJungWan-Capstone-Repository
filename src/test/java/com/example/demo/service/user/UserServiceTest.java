package com.example.demo.service.user;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.Role;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 가입")
    void createUser() {
        User user = User.createUser()
                .email("test@naver.com")
                .password("777")
                .name("홍정완")
                .gender(Gender.MALE)
                .age(25)
                .role(Role.ROLE_USER)
                .phonNumber("010-7175-0000")
                .car_number("14가 540")
                .id("100")
                .build();

        User saveUser = userService.createUser(user);

        Assertions.assertEquals(saveUser, userRepository.findByEmail(saveUser.getEmail()).get());
    }

    @Test
    @DisplayName("이메일로 엔티티 찾기")
    void getUserByEmail() {
        User user = User.createUser()
                .email("test@naver.com")
                .password("777")
                .name("홍정완")
                .gender(Gender.MALE)
                .age(25)
                .role(Role.ROLE_USER)
                .phonNumber("010-7175-0000")
                .car_number("14가 540")
                .id("100")
                .build();

        User saveUser = userService.createUser(user);

        Assertions.assertEquals(userService.getUserByEmail(saveUser.getEmail()), userRepository.findByEmail(saveUser.getEmail()).get());
        Assertions.assertEquals(userService.getUserByEmail("null"), null);

        org.assertj.core.api.Assertions.assertThat(saveUser.getEmail()).isEqualTo("test@naver.com");
    }

}