package com.example.demo.domain.entity;

import com.example.demo.repository.AdminRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AdminTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    @DisplayName("어드민 엔티티 테스트")
    void saveTest() {

        Admin admin = Admin.createAdmin()
                .id(Long.valueOf(1))
                .name("admin")
                .password("admin")
                .build();

        Admin savedAdmin = adminRepository.save(admin);

        Assertions.assertThat(admin.getName()).isEqualTo("admin");
    }
}