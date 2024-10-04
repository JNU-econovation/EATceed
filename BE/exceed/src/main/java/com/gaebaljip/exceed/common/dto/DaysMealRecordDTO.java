package com.gaebaljip.exceed.common.dto;

import java.time.LocalDate;
import java.util.Map;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;

public record DaysMealRecordDTO(Map<LocalDate, DailyMealFoods> mealFoodsByDate) {}
