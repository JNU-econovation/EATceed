package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;
import java.util.Map;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;

public record MonthlyMealRecordDTO(Map<LocalDate, DailyMealFoods> mealFoodsByDate) {}
