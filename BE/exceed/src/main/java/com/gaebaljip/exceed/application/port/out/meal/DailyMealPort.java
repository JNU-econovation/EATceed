package com.gaebaljip.exceed.application.port.out.meal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.common.dto.DailyMealDTO;

@Component
public interface DailyMealPort {
    DailyMealFoods queryDailyMealFoods(DailyMealDTO dailyMealDTO);

    DailyMealFoods queryMealFoods(List<Long> mealIds);
}
