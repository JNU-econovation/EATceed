package com.gaebaljip.exceed.meal.application.port.in;

import com.gaebaljip.exceed.dto.GetMeal;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface GetSpecificMealQuery {
    GetMeal execute(Long memberId, LocalDate date);
}
