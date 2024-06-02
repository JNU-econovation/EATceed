package com.gaebaljip.exceed.meal.application.port.in;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.CurrentMeal;

@Component
public interface GetCurrentMealQuery {
    CurrentMeal execute(Long memberId);
}
