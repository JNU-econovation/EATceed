package com.gaebaljip.exceed.meal.application.port.in;

import com.gaebaljip.exceed.dto.response.SpecificMeal;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface GetSpecificMealQuery {
    SpecificMeal execute(Long memberId, LocalDate date);
}
