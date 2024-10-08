package com.gaebaljip.exceed.common.factory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.common.dto.MonthlyMealRecordDTO;

public class MonthlyMealFixtureFactory {

    /**
     * startDate ~ endDate (양 끝 포함)를 가진 DailyMealFoods를 사용해 MonthlyMeal 생성
     *
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public static MonthlyMealRecordDTO create(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {

        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        List<DailyMealFoods> dailyMealFoodsList =
                startDate
                        .datesUntil(endDate.plusDays(1))
                        .map(date -> DailyMealFoodsFixtureFactory.create(date, date, 3))
                        .toList();
        Map<LocalDate, DailyMealFoods> dateDailyMealFoodsMap =
                dailyMealFoodsList.stream()
                        .collect(
                                Collectors.toMap(
                                        dailyMealFoods ->
                                                dailyMealFoods
                                                        .getMealFoods()
                                                        .get(0)
                                                        .getCreatedDate()
                                                        .toLocalDate(),
                                        dailyMealFoods -> dailyMealFoods));
        MonthlyMealRecordDTO monthlyMealRecordDTO = new MonthlyMealRecordDTO(dateDailyMealFoodsMap);
        return monthlyMealRecordDTO;
    }
}
