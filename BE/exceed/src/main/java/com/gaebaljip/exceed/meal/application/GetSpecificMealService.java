package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.response.CurrentMeal;
import com.gaebaljip.exceed.dto.response.DailyMeal;
import com.gaebaljip.exceed.dto.response.GetFood;
import com.gaebaljip.exceed.dto.response.GetMeal;
import com.gaebaljip.exceed.meal.application.port.in.GetSpecificMealQuery;
import com.gaebaljip.exceed.meal.application.port.out.LoadDailyMealPort;
import com.gaebaljip.exceed.meal.domain.MealModel;
import com.gaebaljip.exceed.meal.domain.MealsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GetSpecificMealService implements GetSpecificMealQuery {

    private final LoadDailyMealPort loadDailyMealPort;

    @Override
    public GetMeal execute(Long memberId, LocalDate date) {
        List<MealModel> mealModels = loadDailyMealPort.queryMealsForDate(memberId, date);
        MealsModel mealsModel = new MealsModel(mealModels);
        List<DailyMeal> dailyMeals = new ArrayList<>();
        setDailyMeals(mealModels, dailyMeals);
        return getGetMeal(mealsModel, dailyMeals);
    }

    private void setDailyMeals(List<MealModel> mealModels, List<DailyMeal> dailyMeals) {
        IntStream.range(0, mealModels.size()).forEach(i -> {
            DailyMeal dailyMeal = DailyMeal.builder()
                    .mealType(mealModels.get(i).getMealType())
                    .time(mealModels.get(i).getMealDateTime().toLocalTime())
                    .foods(mealModels.get(i).getFoodModels().stream().map(foodModel -> GetFood.builder()
                            .id(foodModel.getId())
                            .name(foodModel.getName())
                            .imageUri("https://exceed.s3.ap-northeast-2.amazonaws.com/food/test.jpg")
                            .build()).toList())
                    .build();
            dailyMeals.add(dailyMeal);
        });
    }

    private GetMeal getGetMeal(MealsModel mealsModel, List<DailyMeal> dailyMeals) {
        return GetMeal.builder()
                .currentMeal(getCurrentMeal(mealsModel))
                .dailyMeals(dailyMeals)
                .build();
    }

    private CurrentMeal getCurrentMeal(MealsModel mealsModel) {
        return CurrentMeal.builder()
                .currentCalorie(mealsModel.calculateCurrentCalorie())
                .currentCarbohydrate(mealsModel.calculateCurrentCarbohydrate())
                .currentFat(mealsModel.calculateCurrentFat())
                .currentProtein(mealsModel.calculateCurrentProtein())
                .build();
    }
}
