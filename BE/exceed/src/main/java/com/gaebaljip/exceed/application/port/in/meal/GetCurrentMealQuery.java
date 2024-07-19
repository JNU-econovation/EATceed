package com.gaebaljip.exceed.application.port.in.meal;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.common.dto.CurrentMealDTO;

@Component
public interface GetCurrentMealQuery {
    CurrentMealDTO execute(Long memberId);
}
