package com.gaebaljip.exceed.meal.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.CurrentMealDTO;

@Component
public interface GetCurrentMealQuery {
    CurrentMealDTO execute(Long memberId);
}
