package com.gaebaljip.exceed.member.application.port.in;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.MaintainMeal;

@Component
public interface GetMaintainMealUsecase {
    MaintainMeal execute(Long memberId);

    MaintainMeal execute(Long memberId, LocalDate date);
}
