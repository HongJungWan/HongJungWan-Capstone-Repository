package com.recoder.capstone.repository.custom;

import com.recoder.capstone.domain.entity.AvailableDate;

public interface AvailableDateCustomRepository {

    AvailableDate findAvailableDateByCollegeIdAndDate(Long collegeId, String date);
}
