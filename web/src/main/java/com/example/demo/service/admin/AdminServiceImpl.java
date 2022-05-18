package com.example.demo.service.admin;

import com.example.demo.domain.entity.*;
import com.example.demo.dto.college.*;
import com.example.demo.dto.reserve.ReserveWithUsernameDto;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CollegeRepository;
import com.example.demo.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final CollegeRepository collegeRepository;
    private final AdminRepository adminRepository;
    private final ReserveRepository reserveRepository;

    /**
     * 주차장 정보 등록
     */
    @Transactional
    @Override
    public Long addCollege(CollegeRequestDto collegeRequestDto, String adminName) throws Exception {

        // 주차장 엔티티 생성
        College college = collegeRequestDto.toCollegeEntity();
        /**
         * 현재 Authentication 객체로부터 받은 adminName을 등록하는 주차장의 admin으로 설정하는 방식
         */
        Admin admin = adminRepository.findByName(adminName).get();
        college.setAdmin(admin);
        // 총 주차장 구역 가용 수량 (구역 상관X)
        Integer total = 0;
        // 주차장 구역 엔티티 생성 및 주차장 엔티티에 add
        Map<String, Integer> parkingInfoMap = collegeRequestDto.getParkingInfoMap();
        for (String key : parkingInfoMap.keySet()) {
            Parking parking = Parking.createParking()
                    .parkingName(key)
                    .quantity(parkingInfoMap.get(key))
                    .build();
            parking.addCollege(college);
            total += parkingInfoMap.get(key);
        }
        college.setTotalParkingQuantity(total);

        College savedCollege = collegeRepository.save(college);

        return savedCollege.getId();

    }

    /**
     * 주차장 등록 취소(히든)
     */
    @Transactional
    public void cancelCollege(Long collegeId) {

        //등록 엔티티 조회
        College college = collegeRepository.findOne(collegeId);

        //등록 취소
        college.hidden();
    }
    

    /**
     * 주차장이름으로 주차장 정보 얻어오기
     */
    @Transactional(readOnly = true)
    @Override
    public CollegeResponseDto getCollegeInfo(String collegeName) {
        College findCollege = collegeRepository.findByCollegeName(collegeName)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 주차장입니다.");
                });
        List<Parking> parkings = findCollege.getParkings();
        Map<String, Integer> map = new HashMap<>();
        for (Parking parking : parkings) {
            map.put(parking.getParkingName(), parking.getQuantity());
        }

        // 리턴 고쳐야 함
        return null;
    }

    /**
     * 어드민이 관리하는 주차장 리스트를 보여주기 위한 메서드
     */
    @Override
    public List<CollegeSimpleInfoDto> getAllSimpleCollegeInfo(String name) {
        Admin admin = adminRepository.findByName(name).get();
        return collegeRepository.findAllByAdmin(admin);
    }

    @Override
    public List<CollegeListDto> getCollegeList(String name, String address) {
        Admin admin = adminRepository.findByName(name).get();
        if (address.equals("noSearch")) {
            return collegeRepository.findAllCollegeInfo(admin.getId());
        }

        return collegeRepository.findCollegeListByAddressAndAdmin(address, admin.getId());
    }

    /**
     * 주차장 상세 정보 조회 후 dto로 변환
     */
    @Override
    public CollegeUpdateDto getCollege(Long id) {
        Optional<College> collegeDetail = collegeRepository.findCollegeDetail(id);
        College college = collegeDetail.stream().findFirst().orElse(null);

        List<Parking> parkings = college.getParkings();

        Map<String, Integer> parkingMap = new HashMap<>();

        for (Parking parking : parkings) {
            parkingMap.put(parking.getParkingName(), parking.getQuantity());
        }

        return CollegeUpdateDto.createCollegeUpdateDto()
                .id(college.getId())
                .collegeName(college.getCollegeName())
                .address(college.getAddress())
                .detailAddress(college.getDetailAddress())
                .dateAccept(college.getDateAccept())
                .a(parkingIsPresent(parkingMap, "A 구역"))
                .b(parkingIsPresent(parkingMap, "B 구역"))
                .c(parkingIsPresent(parkingMap, "C 구역"))
                .d(parkingIsPresent(parkingMap, "D 구역"))
                .build();
    }

    @Override
    @Transactional
    public Long collegeUpdate(CollegeUpdateDto dto) throws ParseException {
        Optional<College> collegeDetail = collegeRepository.findCollegeDetail(dto.getId());
        College college = collegeDetail.stream().findFirst().orElse(null);

        //수정 목록
        List<Parking> parkings = college.getParkings();

        //==주차장 구역 수정==//
        Map<String, Integer> parkingInfoMap = dto.getParkingInfoMap();

        Integer total = 0;

        //주차장 구역 이름 리스트. 추가된 주차장 구역, 가용 수량이 0이된 주차장 구역 확인 위해
        List<String> parkingNames = new ArrayList<>();
        for (Parking parking : parkings) {
            parkingNames.add(parking.getParkingName());
        }

        for (String key : parkingInfoMap.keySet()) {
            total += parkingInfoMap.get(key);
            //추가된 주차장 구역이 있는 지 확인
            if (!parkingNames.contains(key)) {
                Parking aditionalParking = Parking.createParking()
                        .parkingName(key)
                        .quantity(parkingInfoMap.get(key))
                        .build();
                aditionalParking.addCollege(college);
            }
            // 기존의 주차장 구역에서 가용 수량이 바뀌었는지 확인
            else {
                for (Parking parking : parkings) {
                    if (parking.getParkingName().equals(key)) {
                        //가용 수량 수정 시, 0을 입력하면 dto로 전달이 안되기 때문에 확인을 위한 과정
                        parkingNames.remove(key);
                        //이미 있는 주차장 구역이라면 가용 수량이 같으면 update 필요 x 가용 수량이 다르면 update
                        if (parking.getQuantity() != parkingInfoMap.get(key)) {
                            parking.updateParkingQty(parkingInfoMap.get(key));
                            parking.setEnabled(true);
                        }
                        break;
                    }
                }
            }
        }
        // 비어있지 않다면, 수정 폼에서 0으로 설정되었다는 뜻. 가용 수량을 0으로 설정하자
        if (!parkingNames.isEmpty()) {
            for (String parkingName : parkingNames) {
                Parking parking = parkings.stream().filter(v -> v.getParkingName().equals(parkingName)).findFirst().orElse(null);
                if (parking != null) {
                    parking.updateParkingQty(0);
                    parking.setEnabled(false);
                }
            }
        }

        //총 가용 수량의 합이 같다면 update x
        if (total != college.getTotalQuantity()) {
            //원래 0이었다면 false 였으니
            if (college.getTotalQuantity() == 0) {
                college.setEnabled(true);
            }

            college.setTotalParkingQuantity(total);

            if (college.getTotalQuantity() == 0) {
                college.setEnabled(false);
            }
        }

        return college.getId();
    }

    /**
     * 주차장 정보 조회 시 , 해당 주차장 구역이 존재하는 지에 대한 여부
     */
    private Integer parkingIsPresent(Map<String, Integer> parkingMap, String key) {
        Integer parkingQty = parkingMap.get(key);

        if (parkingQty == null) {
            return 0;
        }
        return parkingQty;
    }

    /**
     * 예약 현황 정보 얻어오기
     */
    @Transactional(readOnly = true)
    @Override
    public List<ReserveWithUsernameDto> getReserveCondition(Long collegeId) {
        List<Reserve> reserves = reserveRepository.findAllReserve(collegeId);
        if (reserves.isEmpty()) {
            return null;
        }

        return reserves.stream()
                .map(ri -> new ReserveWithUsernameDto(ri))
                .collect(Collectors.toList());
    }

}