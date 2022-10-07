package com.example.demo.repository;

import com.example.demo.dto.reserve.ReserveSimpleDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.demo.domain.value.ReserveStatus.COMP;

@SpringBootTest
@Transactional
class ReserveRepositoryTest {

    @Autowired
    private ReserveRepository reserveRepository;

    @Test
    @DisplayName("예약한 특정 유저 아이디 조회")
    void findByUserId() {
        Optional<ReserveSimpleDto> list = reserveRepository.findByUserId(Long.valueOf(4));

        Assertions.assertThat(list.get().getReserveStatus()).isEqualTo(COMP);
    }
}