package com.recoder.capstone.service.reserve;

import com.recoder.capstone.domain.entity.*;
import com.recoder.capstone.domain.value.ReserveStatus;
import com.recoder.capstone.dto.college.CollegeListDto;
import com.recoder.capstone.dto.parking.ParkingReserveDto;
import com.recoder.capstone.dto.reserve.AvailableDateDto;
import com.recoder.capstone.dto.reserve.AvailableTimeDto;
import com.recoder.capstone.dto.reserve.ReserveItemSimpleDto;
import com.recoder.capstone.repository.*;
import com.recoder.capstone.repository.custom.ParkingCustomRepositoryImpl;
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
public class ReserveItemServiceImpl implements ReserveItemService{

    private final ParkingCustomRepositoryImpl parkingCustomRepository;
    private final CollegeRepository collegeRepository;
    private final AvailableDateRepository availableDateRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final UserRepository userRepository;
    private final ReserveItemRepository reserveItemRepository;


    /**
     * 유저가 예약하기 버튼을 눌렀을 때 모든 주차장의 간단한 정보 (주차장이름, 주소, 주차장 구역남은 자리) 보여주기
     */
    @Override
    public List<CollegeListDto> getAllCollegeInfo(int offset, int limit) {
        return collegeRepository.findCollegeListPaging(offset, limit);
    }

    @Override
    public List<CollegeListDto> getAllCollegeInfoSearchByAddress(String address, int offset, int limit) {

        return collegeRepository.findCollegeListByAddressPaging(offset, limit, address);
    }

    /**
     * 주차장 이름으로 예약가능날짜 조회
     */
    @Override
    public List<AvailableDateDto> getAvailableDates(Long CollegeId) {

        return reserveItemRepository.findAvailableDatesByCollegeId(CollegeId)
                .stream().map( m -> new AvailableDateDto(m.getId(), m.getDate())).collect(Collectors.toList());
    }

    /**
     * 예약가능시간 조회
     */
    public List<AvailableTimeDto> getAvailableTimes(Long id) {

        return reserveItemRepository.findAvailableTimesByAvailableDateId(id)
                .stream().map(t -> new AvailableTimeDto(t.getId(), t.getTime())).collect(Collectors.toList());
    }

    /**
     * 예약가능주차장 구역 조회
     */
    @Override
    public List<ParkingReserveDto> getAvailableParkingNameList(Long collegeId) {
        return reserveItemRepository.findAvailableParkings(collegeId)
                .stream().map(v -> new ParkingReserveDto(v.getId(), v.getParkingName())).collect(Collectors.toList());
    }

    /**
     * 예약처리
     */
    @Override
    public Long reserve(String username, Long collegeId, String parkingName, Long dateId, Long timeId) {
        College college = collegeRepository.findById(collegeId).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 주차장입니다.");
                }
        );
        Parking parking = parkingCustomRepository.findParking(collegeId, parkingName);
        AvailableTime time = availableTimeRepository.findAvailableTimeById(timeId);

        time.decreaseCount();
        if (time.getAcceptCount() <= 0) time.setEnabled(false);

        college.removeStock();

        parking.removeStock();

        AvailableDate availableDate = availableDateRepository.findById(dateId).get();

        User user = userRepository.findByEmail(username).get();

        ReserveItem reserveItem = ReserveItem.createReserveItem()
                .College(college)
                .reserveDate(availableDate.getDate())
                .reserveTime(time.getTime())
                .status(ReserveStatus.COMP)
                .user(user)
                .parkingName(parkingName)
                .build();
        ReserveItem savedReserveItem = reserveItemRepository.save(reserveItem);

        return user.getId();
    }

    /**
     * 예약서 조회
     */
    @Override
    public ReserveItemSimpleDto getReserveResult(String username) {
        log.info("getReserveResult username = {}", username);
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
                }
        );
        return reserveItemRepository.findByUserId(user.getId()).orElseGet(
                () -> { return new ReserveItemSimpleDto(); });
    }

    /**
     * 이미 예약한 회원인지 확인.
     */
    @Override
    public void validateDuplicateUser(String username){
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> {
                    throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
                }
        );
        Optional<ReserveItemSimpleDto> reserveItemByUserId = reserveItemRepository.findByUserId(user.getId());
        if(!reserveItemByUserId.isEmpty()){
            throw new IllegalStateException("이미 예약한 회원 입니다.");
        }

    }

    /**
     * 예약취소
     */
    @Override
    public void cancelReserveItem(Long reserveItemId) {
        ReserveItem reserveItem = reserveItemRepository.findById(reserveItemId).get();

        College college = reserveItem.getCollege();
        college.cancel();

        Parking parking = parkingCustomRepository.findParkingDisabled(college.getId(), reserveItem.getParkingName());
        parking.cancel();

        AvailableDate date = availableDateRepository.findAvailableDateByCollegeIdAndDate(college.getId(), reserveItem.getReserveDate());
        AvailableTime time = availableTimeRepository.findAvailableTimeByTimeAndDateId(reserveItem.getReserveTime(), date.getId());
        time.cancel();

        reserveItemRepository.deleteById(reserveItemId);
    }
}
