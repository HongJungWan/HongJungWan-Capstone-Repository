package com.example.demo.domain.entity;

import com.example.demo.repository.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AdminTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    @DisplayName("어드민 엔티티 테스트")
    void saveTest() {

        Admin admin = Admin.createAdmin()
                .id(Long.valueOf(2))
                .name("홍정완")
                .password("1234")
                .build();

        Admin savedAdmin = adminRepository.save(admin);

        assertThat(savedAdmin.getName()).isEqualTo("홍정완");
        assertThat(savedAdmin.getId()).isEqualTo(2);
        assertThat(savedAdmin.getPassword()).isEqualTo("1234");

    }

}