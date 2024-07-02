package com.gaebaljip.exceed.meal.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.CurrentMealDTO;

@Component
public interface GetCurrentMealQuery {
    CurrentMealDTO execute(Long memberId);
}
