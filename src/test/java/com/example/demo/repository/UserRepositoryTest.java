package com.example.demo.repository;

import com.example.demo.domain.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@WebAppConfiguration
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 특정 이메일 조회")
    void findByEmail() {
        Optional<User> list = userRepository.findByEmail("admin");

        System.out.println("UserRepositoryTest.findByEmail = " + list.get().getEmail());

        Assertions.assertThat(list.get().getEmail()).isEqualTo("admin");
    }
}