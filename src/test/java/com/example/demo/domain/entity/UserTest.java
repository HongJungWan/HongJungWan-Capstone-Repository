package com.example.demo.domain.entity;

import com.example.demo.domain.value.Gender;
import com.example.demo.domain.value.Role;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 엔티티 테스트")
    void saveTest() {
        // given
        User user = User.createUser()
                .id("1000")
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
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getEmail()).isEqualTo("test@naver.com");
        assertThat(savedUser.getName()).isEqualTo("홍");
        assertThat(savedUser.getCar_number()).isEqualTo("12가 1212");
        assertThat(savedUser.getPassword()).isEqualTo("1234");
        assertThat(savedUser.getAge()).isEqualTo(25);
        assertThat(savedUser.getRole()).isEqualTo(Role.ROLE_USER);
        assertThat(savedUser.getPhonNumber()).isEqualTo("010-0000-0000");
        assertThat(savedUser.getGender()).isEqualTo(Gender.MALE);

    }

}