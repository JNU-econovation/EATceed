package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.response.CurrentMeal;
import com.gaebaljip.exceed.meal.domain.MealModel;
import com.gaebaljip.exceed.meal.application.port.out.LoadMealPort;
import com.gaebaljip.exceed.meal.application.port.in.GetCurrentMealQuery;
import com.gaebaljip.exceed.meal.domain.MealsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCurrentMealService implements GetCurrentMealQuery {

    private final LoadMealPort loadMealPort;

    @Override
    public CurrentMeal execute(Long memberId, LocalDate date) {
        List<MealModel> mealModels = loadMealPort.query(memberId, date);
        MealsModel mealsModel = MealsModel.builder()
                .mealModels(mealModels)
                .build();
        return CurrentMeal.builder()
                .currentCalorie(mealsModel.calculateCurrentCalorie())
                .currentCarbohydrate(mealsModel.calculateCurrentCarbohydrate())
                .currentFat(mealsModel.calculateCurrentFat())
                .currentProtein(mealsModel.calculateCurrentProtein())
                .build();
    }
}
