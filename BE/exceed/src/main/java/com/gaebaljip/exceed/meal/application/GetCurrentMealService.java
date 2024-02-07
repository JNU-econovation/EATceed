package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.response.CurrentMeal;
import com.gaebaljip.exceed.meal.domain.MealModel;
import com.gaebaljip.exceed.meal.application.port.out.LoadDailyMealPort;
import com.gaebaljip.exceed.meal.application.port.in.GetCurrentMealQuery;
import com.gaebaljip.exceed.meal.domain.MealsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCurrentMealService implements GetCurrentMealQuery {

    private final LoadDailyMealPort loadDailyMealPort;

    @Override
    @Transactional(readOnly = true)
    public CurrentMeal execute(Long memberId) {
        List<MealModel> mealModels = loadDailyMealPort.queryMealsForDate(memberId, LocalDate.now());
        MealsModel mealsModel = new MealsModel(mealModels);
        return CurrentMeal.builder()
                .calorie(mealsModel.calculateCurrentCalorie())
                .carbohydrate(mealsModel.calculateCurrentCarbohydrate())
                .fat(mealsModel.calculateCurrentFat())
                .protein(mealsModel.calculateCurrentProtein())
                .build();
    }
}
