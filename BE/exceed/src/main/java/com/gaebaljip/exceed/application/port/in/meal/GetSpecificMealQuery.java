package com.gaebaljip.exceed.application.port.in.meal;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.dto.SpecificMealDTO;

@Component
public interface GetSpecificMealQuery {
    SpecificMealDTO execute(Long memberId, LocalDateTime date);
}
