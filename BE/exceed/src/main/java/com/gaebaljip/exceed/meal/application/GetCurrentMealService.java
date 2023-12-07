package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.CurrentMeal;
import com.gaebaljip.exceed.meal.domain.MealModel;
import com.gaebaljip.exceed.meal.port.out.LoadMealPort;
import com.gaebaljip.exceed.meal.port.in.GetCurrentMealQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GetCurrentMealService implements GetCurrentMealQuery {

    private final LoadMealPort loadMealPort;

    @Override
    public CurrentMeal execute(Long memberId, LocalDate date) {
        MealModel mealModel = loadMealPort.loadMeal(memberId, date);
        CurrentMeal currentMeal = CurrentMeal.builder()
                .currentCalorie(mealModel.getCurrentCalorie())
                .currentCarbohydrate(mealModel.getCurrentCarbohydrate())
                .currentProtein(mealModel.getCurrentProtein())
                .currentFat(mealModel.getCurrentFat())
                .build();
        return currentMeal;
    }
}
