package com.recoder.capstone.repository.custom;

import com.recoder.capstone.domain.entity.AvailableTime;

public interface AvailableTimeCustomRepository {

    AvailableTime findAvailableTimeById(Long timeId);

    AvailableTime findAvailableTimeByTimeAndDateId(Integer time, Long dateId);
}
