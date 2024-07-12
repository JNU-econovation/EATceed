package com.gaebaljip.exceed.application.port.out.meal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.dto.TodayMealDTO;

@Component
public interface DailyMealPort {
    List<Meal> query(TodayMealDTO todayMealDTO);
}
