package com.gaebaljip.exceed.member.application.port.in;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.TargetMeal;

@Component
public interface GetTargetMealUsecase {
    TargetMeal execute(Long memberId);

    TargetMeal execute(Long memberId, LocalDate date);
}
