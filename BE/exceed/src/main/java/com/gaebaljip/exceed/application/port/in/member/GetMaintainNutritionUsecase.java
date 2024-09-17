package com.gaebaljip.exceed.application.port.in.member;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.dto.MaintainMealDTO;

@Component
public interface GetMaintainNutritionUsecase {
    MaintainMealDTO execute(Long memberId);

    MaintainMealDTO execute(Long memberId, LocalDateTime date);
}
