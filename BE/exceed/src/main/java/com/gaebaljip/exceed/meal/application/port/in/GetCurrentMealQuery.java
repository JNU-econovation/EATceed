package com.gaebaljip.exceed.meal.application.port.in;

import com.gaebaljip.exceed.dto.CurrentMeal;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface GetCurrentMealQuery {
    CurrentMeal execute(Long memberId, LocalDate date);
}
