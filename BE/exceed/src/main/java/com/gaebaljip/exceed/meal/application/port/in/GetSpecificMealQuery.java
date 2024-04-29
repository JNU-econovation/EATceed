package com.gaebaljip.exceed.meal.application.port.in;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.SpecificMeal;

@Component
public interface GetSpecificMealQuery {
    SpecificMeal execute(Long memberId, LocalDateTime date);
}
