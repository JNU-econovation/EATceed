package com.gaebaljip.exceed.meal.application.port.in;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.SpecificMeal;

@Component
public interface GetSpecificMealQuery {
    SpecificMeal execute(Long memberId, LocalDate date);
}
