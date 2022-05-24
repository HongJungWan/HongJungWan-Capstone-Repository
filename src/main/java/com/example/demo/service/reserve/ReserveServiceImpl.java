package com.example.demo.service.reserve;

import com.example.demo.domain.entity.College;
import com.example.demo.domain.entity.Parking;
import com.example.demo.domain.entity.Reserve;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.value.ReserveStatus;
import com.example.demo.dto.Parking.ParkingReserveDto;
import com.example.demo.dto.college.CollegeListUserDto;
import com.example.demo.dto.reserve.ReserveSimpleDto;
import com.example.demo.repository.CollegeRepository;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.custom.ParkingCustomRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReserveServiceImpl implements ReserveService {

    private final ParkingCustomRepositoryImpl parkingCustomRepository;

    private final CollegeRepository collegeRepository;

    private final UserRepository userRepository;

    private final ReserveRepository reserveRepository;

    /**
     * 유저가 예약하기 버튼을 눌렀을 때 모든 주차장의 간단한 정보 (주차장 이름, 주소, 주차 구역 남은 자리) 보여주기
     */
    @Override
    public List<CollegeListUserDto> getAllCollegeInfo(int offset, int limit) {

        return collegeRepository.findCollegeListPaging(offset, limit);

    }

    @Override
    public List<CollegeListUserDto> getAllCollegeInfoSearchByAddress(String address, int offset, int limit) {

        return collegeRepository.findCollegeListByAddressPaging(offset, limit, address);

    }

    /**
     * 예약 가능 주차 구역 조회
     */
    @Override
    public List<ParkingReserveDto> getAvailableParkingNameList(Long collegeId) {
        return reserveRepository.findAvailableParkings(collegeId)
                .stream().map(v -> new ParkingReserveDto(v.getId(), v.getParkingName())).collect(Collectors.toList());
    }


    /**
     * 예약처리
     */
    @Override
    public Long reserve(String username, Long collegeId, String parkingName) {

        College college = collegeRepository.findById(collegeId).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 주차장입니다.");
                }
        );
        Parking parking = parkingCustomRepository.findParking(collegeId, parkingName);

        college.removeStock();

        parking.removeStock();


        User user = userRepository.findByEmail(username).get();

        Reserve reserve = Reserve.createReserve()
                .College(college)
                .status(ReserveStatus.COMP)
                .user(user)
                .parkingName(parkingName)
                .build();
        Reserve savedReserve = reserveRepository.save(reserve);

        return user.getUser_id();

    }

    /**
     * 예약 조회
     */
    @Override
    public ReserveSimpleDto getReserveResult(String username) {

        log.info("getReserveResult username = {}", username);
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
                }
        );
        return reserveRepository.findByUserId(user.getUser_id()).orElseGet(
                () -> {
                    return new ReserveSimpleDto();
                });

    }

    /**
     * 하나의 주차 구역만 예약 가능, 중복 방지.
     */
    @Override
    public void validateDuplicateUser(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
                }
        );

        Optional<ReserveSimpleDto> reserveByUserId = reserveRepository.findByUserId(user.getUser_id());
        if (!reserveByUserId.isEmpty()) {
            throw new IllegalStateException("이미 예약한 회원 입니다.");
        }

    }

    /**
     * 예약취소
     */
    @Override
    public void cancelReserve(Long reserveId) {

        Reserve reserve = reserveRepository.findById(reserveId).get();

        College college = reserve.getCollege();
        college.cancel();

        Parking parking = parkingCustomRepository.findParkingDisabled(college.getId(), reserve.getParkingName());
        parking.cancel();

        reserveRepository.deleteById(reserveId);

    }

}
