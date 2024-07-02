package com.gaebaljip.exceed.meal.application.port.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.TodayMealDTO;
import com.gaebaljip.exceed.meal.domain.Meal;

@Component
public interface DailyMealPort {
    List<Meal> query(TodayMealDTO todayMealDTO);
}
