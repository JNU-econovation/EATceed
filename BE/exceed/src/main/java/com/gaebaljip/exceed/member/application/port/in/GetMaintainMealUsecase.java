package com.gaebaljip.exceed.member.application.port.in;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.MaintainMealDTO;

@Component
public interface GetMaintainMealUsecase {
    MaintainMealDTO execute(Long memberId);

    MaintainMealDTO execute(Long memberId, LocalDateTime date);
}
