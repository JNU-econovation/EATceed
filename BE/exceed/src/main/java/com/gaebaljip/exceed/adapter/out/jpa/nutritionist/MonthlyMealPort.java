package com.gaebaljip.exceed.adapter.out.jpa.nutritionist;

import java.util.List;

import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.dto.MonthlyMealDTO;

public interface MonthlyMealPort {
    List<Meal> query(MonthlyMealDTO monthlyMealDTO);
}
