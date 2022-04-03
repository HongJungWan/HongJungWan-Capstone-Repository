package com.recoder.hong.service.admin;

import com.recoder.hong.domain.entity.*;
import com.recoder.hong.dto.college.*;
import com.recoder.hong.repository.*;
import com.recoder.hong.service.Holiday;
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
    private final Holiday holiday;
    private final AvailableTimeRepository availableTimeRepository;
    private final AvailableDateRepository availableDateRepository;

    /**
     * 주차장 정보 등록
     */
    @Transactional
    @Override
    public Long addCollege(CollegeRequestDto collegeRequestDto, String adminName) throws Exception{

        // 주차장 엔티티 생성
        College college = collegeRequestDto.toCollegeEntity();
        /**
         * 현재 Authentication 객체로부터 받은 adminName을 등록하는 병원의 admin으로 설정하는 방식
         */
        Admin admin = adminRepository.findByName(adminName).get();
        college.setAdmin(admin);
        // 총 주차 구역 수 (종류 상관X)
        Integer total = 0;
        // 주차 구역 엔티티 생성 및 주차장 엔티티에 add
        Map<String, Integer> parkingareaInfoMap = collegeRequestDto.getParkingareaInfoMap();
        for (String key : parkingareaInfoMap.keySet()) {
            ParkingArea parkingarea = ParkingArea.createParkingArea()
                    .parkingareaName(key)
                    .quantity(parkingareaInfoMap.get(key))
                    .build();
            parkingarea.addCollege(college);
            total += parkingareaInfoMap.get(key);
        }
        college.setTotalParkingAreaQuantity(total);

        /**
         * 예약 가능 날짜를 생성 (휴일제외)
         */
        // 예약가능시간
        List<Integer> availableTimeList = getAvailableTimes(collegeRequestDto.getStartTime(), collegeRequestDto.getEndTime());

        // 예약가능날짜
        List<String> holidays = holiday.holidayList(collegeRequestDto.getStartDate(), collegeRequestDto.getEndDate());
        List<String> availableDateList = holiday.availableDateList(collegeRequestDto.getStartDate(), collegeRequestDto.getEndDate(), holidays);

        for (String date : availableDateList) {
            AvailableDate availableDate= AvailableDate.createAvailableDate()
                    .date(date)
                    .acceptCount(collegeRequestDto.getDateAccept())
                    .build();
            for (Integer time : availableTimeList) {
                AvailableTime availableTime= AvailableTime.createAvailableTime()
                        .time(time)
                        .acceptCount(collegeRequestDto.getTimeAccept())
                        .build();
                availableTime.addAvailableDate(availableDate);
            }
            availableDate.addCollege(college);
        }


        College savedCollege = collegeRepository.save(college);

        return savedCollege.getId();
    }

    /**
     * 예약가능시간 처리 메서드
     */
    private List<Integer> getAvailableTimes(String startTime, String endTime) {
        int start = Integer.parseInt(startTime);
        int end = Integer.parseInt(endTime);
        List<Integer> availableTimes = new ArrayList<>();
        for (int i=start; i<=end;i++) {
            availableTimes.add(i);
        }
        return availableTimes;
    }

    /**
     * 주차장 이름으로 주차장 정보 얻어오기
     */
    @Transactional(readOnly = true)
    @Override
    public CollegeResponseDto getCollegeInfo(String collegeName) {
        College findCollege = collegeRepository.findByCollegeName(collegeName)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않는 병원입니다.");
                });
        List<ParkingArea> parkingareas = findCollege.getParkingareas();
        Map<String, Integer> map = new HashMap<>();
        for (ParkingArea parkingarea : parkingareas) {
            map.put(parkingarea.getParkingareaName(), parkingarea.getQuantity());
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
        if(address.equals("noSearch"))
            return collegeRepository.findAllCollegeInfo(admin.getId());

        return collegeRepository.findCollegeListByAddressAndAdmin(address, admin.getId());
    }

    /**
     * 주차장 상세 정보 조회 후 dto로 변환
     */
    @Override
    public CollegeUpdateDto getCollege(Long id) {
        Optional<College> collegeDetail = collegeRepository.findCollegeDetail(id);
        College college = collegeDetail.stream().findFirst().orElse(null);

        List<AvailableDate> availableDates = college.getAvailableDates();
        List<AvailableTime> availableTimes = availableDates.get(0).getAvailableTimes();
        List<ParkingArea> parkingareas = college.getParkingareas();

        Map<String,Integer> parkingareaMap=new HashMap<>();

        for (ParkingArea parkingarea : parkingareas) {
            parkingareaMap.put(parkingarea.getParkingareaName(),parkingarea.getQuantity());
        }

        return CollegeUpdateDto.createCollegeUpdateDto()
                .id(college.getId())
                .collegeName(college.getCollegeName())
                .address(college.getAddress())
                .detailAddress(college.getDetailAddress())
                .dateAccept(college.getDateAccept())
                .timeAccept(college.getTimeAccept())
                .startDate(availableDates.get(0).getDate())
                .endDate(availableDates.get(availableDates.size()-1).getDate())
                .startTime(String.valueOf(availableTimes.get(0).getTime()))
                .endTime(String.valueOf(availableTimes.get(availableTimes.size()-1).getTime()))
                .a(parkingareaIsPresent(parkingareaMap,"a"))
                .b(parkingareaIsPresent(parkingareaMap,"b"))
                .c(parkingareaIsPresent(parkingareaMap,"c"))
                .d(parkingareaIsPresent(parkingareaMap,"d"))
                .build();
    }

    @Override
    @Transactional
    public Long collegeUpdate(CollegeUpdateDto dto) throws ParseException {
        Optional<College> collegeDetail = collegeRepository.findCollegeDetail(dto.getId());
        College college = collegeDetail.stream().findFirst().orElse(null);

        //수정 목록
        List<ParkingArea> parkingareas = college.getParkingareas();

        //==백신 수정==//
        Map<String, Integer> parkingareaInfoMap = dto.getParkingareaInfoMap();

        Integer total = 0;

        //백신 이름 리스트. 추가된 백신, 수량이 0이된 백신 확인 위해
        List<String> parkingareaNames=new ArrayList<>();
        for (ParkingArea parkingarea : parkingareas) {
            parkingareaNames.add(parkingarea.getParkingareaName());
        }

        for(String key:parkingareaInfoMap.keySet()){
            total+=parkingareaInfoMap.get(key);
            //추가된 백신이 있는 지 확인
            if(!parkingareaNames.contains(key)){
                ParkingArea aditionalParkingArea = ParkingArea.createParkingArea()
                        .parkingareaName(key)
                        .quantity(parkingareaInfoMap.get(key))
                        .build();
                aditionalParkingArea.addCollege(college);
            }
            // 기존의 백신에서 수량이 바뀌었는지 확인
            else {
                for (ParkingArea parkingarea : parkingareas) {
                    if (parkingarea.getParkingareaName().equals(key)) {
                        //수량 수정 시, 0을 입력하면 dto로 전달이 안되기 때문에 확인을 위한 과정
                        parkingareaNames.remove(key);
                        //이미 있는 백신이라면 수량이 같으면 update 필요 x 수량이 다르면 update
                        if (parkingarea.getQuantity() != parkingareaInfoMap.get(key)) {
                            parkingarea.updateParkingAreaQty(parkingareaInfoMap.get(key));
                            parkingarea.setEnabled(true);
                        }
                        break;
                    }
                }
            }
        }
        // 비어있지 않다면, 수정 폼에서 0으로 설정되었다는 뜻. 수량을 0으로 설정하자
        if(!parkingareaNames.isEmpty()){
            for (String parkingareaName : parkingareaNames) {
                ParkingArea parkingarea = parkingareas.stream().filter(v -> v.getParkingareaName().equals(parkingareaName)).findFirst().orElse(null);
                if(parkingarea!=null){
                    parkingarea.updateParkingAreaQty(0);
                    parkingarea.setEnabled(false);
                }
            }
        }

        //총 수량의 합이 같다면 update x
        if(total!=college.getTotalQuantity()) {
            //원래 0이었다면 false 였으니
            if(college.getTotalQuantity()==0)
                college.setEnabled(true);

            college.setTotalParkingAreaQuantity(total);

            if(college.getTotalQuantity()==0)
                college.setEnabled(false);
        }

        //병원의 예약가능 날짜 리스트
        List<AvailableDate> availableDates = college.getAvailableDates();

        //==dateAccept수정부분==//
        Integer dateAcceptCount = dto.getDateAccept();
        Integer originDateAccept = college.getDateAccept();
        //dateAccept가 수정되었다면
        if(originDateAccept != dateAcceptCount){
            college.updateDateAccept(dateAcceptCount);
            int updateDateAcceptCount = dateAcceptCount - originDateAccept;

            List<Long> availableDateIds=new ArrayList<>();

            //수정된 dateAccept 적용 시, 0보다 작거나 같아질 경우
            boolean flag=false;
            for (AvailableDate availableDate : availableDates) {
                if(availableDate.getAcceptCount()+updateDateAcceptCount<=0){
                    availableDateIds.add(availableDate.getId());
                    flag=true;
                }
            }
            availableDateRepository.updateAvailableDateAcceptCount(updateDateAcceptCount
                    ,college.getId());
            if(flag)
            {
                availableDateRepository.updateAvailableDateAcceptCountToZero(availableDateIds);
            }
        }

        //==timeAccept수정부분==//
        Integer timeAcceptCount = dto.getTimeAccept();
        Integer originTimeAccept = college.getTimeAccept();

        //timeAccept가 수정되었다면
        if(originTimeAccept !=timeAcceptCount){
            int updateAcceptCount = timeAcceptCount - originTimeAccept;

            college.updateTimeAccept(timeAcceptCount);

            List<Long> availableDateIds=new ArrayList<>();
            List<Long> availableTimeIds=new ArrayList<>();

            boolean flag=false;
            for (AvailableDate availableDate : availableDates) {
                availableDateIds.add(availableDate.getId());
                List<AvailableTime> availableTimes = availableDate.getAvailableTimes();

                //수량이 0보다 작거나 같아지는 것이 있으면
                for (AvailableTime availableTime : availableTimes) {
                    if(availableTime.getAcceptCount()+updateAcceptCount<=0){
                        availableTimeIds.add(availableTime.getId());
                        flag=true;
                    }
                }
            }
            availableTimeRepository.updateAvailableTimeAcceptCount(updateAcceptCount
                    ,availableDateIds);
            if(flag){
                availableTimeRepository.updateAvailableTimeAcceptCountToZero(availableTimeIds);
            }
        }

        return college.getId();
    }



    /**
     * 병원 정보 조회 시 , 해당 백신이 존재하는 지에 대한 여부
     */
    private Integer parkingareaIsPresent(Map<String, Integer> parkingareaMap,String key){
        Integer parkingareaQty = parkingareaMap.get(key);

        if(parkingareaQty ==null)
            return 0;
        return parkingareaQty;
    }
}