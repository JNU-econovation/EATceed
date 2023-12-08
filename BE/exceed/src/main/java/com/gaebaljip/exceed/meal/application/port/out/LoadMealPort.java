package com.gaebaljip.exceed.meal.application.port.out;

import com.gaebaljip.exceed.meal.domain.MealModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface LoadMealPort {
    MealModel query(Long memberId, LocalDate date);
}
