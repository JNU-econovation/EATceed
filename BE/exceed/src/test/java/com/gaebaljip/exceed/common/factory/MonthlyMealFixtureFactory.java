package com.gaebaljip.exceed.common.factory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.nutritionist.MonthlyMeal;

public class MonthlyMealFixtureFactory {
    public static MonthlyMeal create(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Meal> meals =
                MealsFixtureFactory.create(
                        startDateTime.toLocalDate(), endDateTime.toLocalDate(), 40);
        Map<LocalDate, DailyMeal> dailyMealMap =
                meals.stream()
                        .collect(
                                Collectors.groupingBy(
                                        meal -> meal.getMealDateTime().toLocalDate(),
                                        Collectors.collectingAndThen(
                                                Collectors.toList(), DailyMeal::new)));

        List<Meal> emptyMeal = new ArrayList<>();
        startDateTime
                .toLocalDate()
                .datesUntil(endDateTime.toLocalDate())
                .forEach(day -> dailyMealMap.putIfAbsent(day, new DailyMeal(emptyMeal)));

        MonthlyMeal monthlyMeal = new MonthlyMeal(dailyMealMap);
        return monthlyMeal;
    }
}
