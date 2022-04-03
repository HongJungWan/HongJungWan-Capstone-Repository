package com.recoder.hong.repository.custom;

import com.recoder.hong.domain.entity.AvailableDate;

public interface AvailableDateCustomRepository {

    AvailableDate findAvailableDateByCollegeIdAndDate(Long collegeId, String date);
}
