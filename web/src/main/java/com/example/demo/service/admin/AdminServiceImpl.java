package com.example.demo.service.admin;


import com.example.demo.domain.entity.Admin;
import com.example.demo.domain.entity.AvailableDate;
import com.example.demo.domain.entity.Collage;
import com.example.demo.domain.entity.Parking;
import com.example.demo.dto.collage.*;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CollageRepository;
import com.ibm.icu.util.Holiday;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final CollageRepository collageRepository;
    private final AdminRepository adminRepository;


    /**
     * 주차장 정보 등록
     */
    @Transactional
    @Override
    public Long addCollage(CollageRequestDto collageRequestDto, String adminName) throws Exception{

        // 주차장 엔티티 생성
        Collage collage = collageRequestDto.toCollageEntity();
        /**
         * 현재 Authentication 객체로부터 받은 adminName을 등록하는 주차장의 admin으로 설정하는 방식
         */
        Admin admin = adminRepository.findByName(adminName).get();
        collage.setAdmin(admin);
        // 총 주차장 구역 가용 수량 (종류 상관X)
        Integer total = 0;
        // 주차장 구역 엔티티 생성 및 주차장 엔티티에 add
        Map<String, Integer> parkingInfoMap = collageRequestDto.getParkingInfoMap();
        for (String key : parkingInfoMap.keySet()) {
            Parking parking = Parking.createParking()
                    .parkingName(key)
                    .quantity(parkingInfoMap.get(key))
                    .build();
            parking.addCollage(collage);
            total += parkingInfoMap.get(key);
        }
        collage.setTotalParkingQuantity(total);


        Collage savedCollage = collageRepository.save(collage);

        return savedCollage.getId();
    }



    /**
     * 주차장이름으로 주차장 정보 얻어오기
     */
    @Transactional(readOnly = true)
    @Override
    public CollageResponseDto getCollageInfo(String collageName) {
        Collage findCollage = collageRepository.findByCollageName(collageName)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 주차장입니다.");
                });
        List<Parking> parkings = findCollage.getParkings();
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
    public List<CollageSimpleInfoDto> getAllSimpleCollageInfo(String name) {
        Admin admin = adminRepository.findByName(name).get();
        return collageRepository.findAllByAdmin(admin);
    }

    @Override
    public List<CollageListDto> getCollageList(String name, String address) {
        Admin admin = adminRepository.findByName(name).get();
        if(address.equals("noSearch"))
            return collageRepository.findAllCollageInfo(admin.getId());

        return collageRepository.findCollageListByAddressAndAdmin(address, admin.getId());
    }

    /**
     * 주차장 상세 정보 조회 후 dto로 변환
     */
    @Override
    public CollageUpdateDto getCollage(Long id) {
        Optional<Collage> collageDetail = collageRepository.findCollageDetail(id);
        Collage collage = collageDetail.stream().findFirst().orElse(null);

        List<AvailableDate> availableDates = collage.getAvailableDates();
        List<Parking> parkings = collage.getParkings();

        Map<String,Integer> parkingMap=new HashMap<>();

        for (Parking parking : parkings) {
            parkingMap.put(parking.getParkingName(), parking.getQuantity());
        }

        return CollageUpdateDto.createCollageUpdateDto()
                .id(collage.getId())
                .collageName(collage.getCollageName())
                .address(collage.getAddress())
                .detailAddress(collage.getDetailAddress())
                .startDate(availableDates.get(0).getDate())
                .endDate(availableDates.get(availableDates.size()-1).getDate())
                .a(parkingIsPresent(parkingMap,"A 구역"))
                .b(parkingIsPresent(parkingMap,"B 구역"))
                .c(parkingIsPresent(parkingMap,"C 구역"))
                .d(parkingIsPresent(parkingMap,"D 구역"))
                .build();
    }


    /**
     * 주차장 정보 조회 시 , 해당 주차장 구역이 존재하는 지에 대한 여부
     */
    private Integer parkingIsPresent(Map<String, Integer> parkingMap,String key){
        Integer parkingQty = parkingMap.get(key);

        if(parkingQty ==null)
            return 0;
        return parkingQty;
    }
}