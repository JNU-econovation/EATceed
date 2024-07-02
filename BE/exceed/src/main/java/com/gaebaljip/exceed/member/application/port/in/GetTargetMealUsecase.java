package com.gaebaljip.exceed.member.application.port.in;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.TargetMealDTO;

@Component
public interface GetTargetMealUsecase {
    TargetMealDTO execute(Long memberId);

    TargetMealDTO execute(Long memberId, LocalDateTime date);
}
