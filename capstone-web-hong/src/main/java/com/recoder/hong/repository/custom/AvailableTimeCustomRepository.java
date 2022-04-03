package com.recoder.hong.repository.custom;


import com.recoder.hong.domain.entity.AvailableTime;

public interface AvailableTimeCustomRepository {

    AvailableTime findAvailableTimeById(Long timeId);

    AvailableTime findAvailableTimeByTimeAndDateId(Integer time, Long dateId);
}
