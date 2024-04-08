package com.gaebaljip.exceed.member.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.TargetMeal;

@Component
public interface GetTargetMealUsecase {
    TargetMeal execute(Long memberId);
}
