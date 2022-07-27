package com.example.demo.repository;

import com.example.demo.domain.entity.Admin;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    @DisplayName("어드민 이름 조회")
    void findByName() {
        Optional<Admin> list = adminRepository.findByName("admin");

        System.out.println("\nadminRepositoryTest.findByName = " + list.get().getName());

        Assertions.assertThat(list.get().getName()).isEqualTo("admin");
    }
}