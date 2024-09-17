package com.gaebaljip.exceed.application.port.in.member;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.dto.TargetMealDTO;

@Component
public interface GetTargetNutritionUsecase {
    TargetMealDTO execute(Long memberId);

    TargetMealDTO execute(Long memberId, LocalDateTime date);
}
