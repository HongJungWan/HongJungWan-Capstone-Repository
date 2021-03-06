package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.Role;
import com.example.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 엔티티 테스트")
    void saveTest() {
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
        User savedUser = userRepository.save(user);

        Assertions.assertThat(user.getEmail()).isEqualTo("test@naver.com");
    }
}